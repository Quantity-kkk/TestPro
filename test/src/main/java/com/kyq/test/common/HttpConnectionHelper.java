package com.kyq.test.common;

//import org.apache.struts2.json.JSONException;
//import org.apache.struts2.json.JSONUtil;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Description:  HttpConnectionHelper 用于Http测试的帮助类。
 *
 * @author kuangyongqiang
 * @version 1.0
 */
public class HttpConnectionHelper {

    public static String doPost(String url, Map<String,String> params){
        OutputStreamWriter out = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            URL uri = new URL(url);// http://101.204.240.105:50040/scdp/controller/json.action
            HttpURLConnection httpConn = (HttpURLConnection) uri.openConnection();
            httpConn.setRequestMethod("POST");//POST,GET
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out =  new OutputStreamWriter(httpConn.getOutputStream(),"utf-8");

            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);

            System.out.println("send_url:" + url);
            System.out.println("send_data:" + sb.toString());

            out.write(sb.toString());

            out.flush();
            isr = new InputStreamReader(httpConn.getInputStream(),"utf-8");
            br = new BufferedReader(isr);
            StringBuilder sbRet = new StringBuilder();
            String str;
            while((str = br.readLine())!=null){
                sbRet.append(str + "\r\n");
            }
            System.out.println("response_data:" + sbRet.toString());
            return sbRet.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(out!=null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(br!=null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(isr!=null)
                    isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String doPostWithFile2(String url,String filePath,Map<String,String> params){
        String boundary = "----"+new Date().getTime();

        DataOutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileInputStream fStream = null;

        try {
            URL uri = new URL(url);// http://101.204.240.105:50040/scdp/controller/json.action
            HttpURLConnection httpConn = (HttpURLConnection) uri.openConnection();
            httpConn.setRequestMethod("POST");//POST,GET
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out =  new DataOutputStream(httpConn.getOutputStream());

            //text
            StringBuffer textBuf = new StringBuffer();
            Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                String inputName = (String) entry.getKey();
                String inputValue = (String) entry.getValue();
                if (inputValue == null) {
                    continue;
                }
                textBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                textBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");

                textBuf.append(inputValue);
            }

            System.out.println("send_url:" + url);
            System.out.println("send_text_data:" + textBuf.toString());
            out.writeBytes(textBuf.toString());

            out.flush();

            //file
            File file = new File(filePath);
            String filename = file.getName();

            StringBuffer strBuf = new StringBuffer();
            strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
            strBuf.append("Content-Disposition: form-data; name=\"uploadFile\"; filename=\"" + filename + "\"\r\n");
            strBuf.append("Content-Type:application/octet-stream\r\n\r\n");
            out.writeBytes(strBuf.toString());
            System.out.println("send_file_data:" + strBuf.toString());
            fStream = new FileInputStream(filePath);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
			/* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
                out.write(buffer,0,length);
            }

            out.writeBytes("\r\n--"+boundary+"--\r\n");//文件上传结束
            System.out.println("\r\n--"+boundary+"--\r\n");
            isr = new InputStreamReader(httpConn.getInputStream(),"utf-8");
            br = new BufferedReader(isr);
            StringBuilder sbRet = new StringBuilder();
            String str;
            while((str = br.readLine())!=null){
                sbRet.append(str + "\r\n");
            }
            System.out.println("response_data:" + sbRet.toString());
            return sbRet.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(out!=null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(br!=null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(isr!=null)
                    isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fStream!=null){
                    fStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    public static String doPostWithFile(String url,String filePath,Map<String,String> params){
        String boundary = "----"+new Date().getTime();

        DataOutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileInputStream fStream = null;

        try {
            URL uri = new URL(url);// http://101.204.240.105:50040/scdp/controller/json.action
            HttpURLConnection httpConn = (HttpURLConnection) uri.openConnection();
            httpConn.setRequestMethod("POST");//POST,GET
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            out =  new DataOutputStream(httpConn.getOutputStream());

            //text
            StringBuffer textBuf = new StringBuffer();
            Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                String inputName = (String) entry.getKey();
                String inputValue = (String) entry.getValue();
                if (inputValue == null) {
                    continue;
                }
                textBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                textBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                textBuf.append(inputValue);
            }

            System.out.println("send_url:" + url);
            System.out.println("send_text_data:" + textBuf.toString());
            out.writeBytes(textBuf.toString());

            out.flush();

            //file
            File file = new File(filePath);
            String filename = file.getName();

            StringBuffer strBuf = new StringBuffer();
            strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
            strBuf.append("Content-Disposition: form-data; name=\"uploadFile\"; filename=\"" + filename + "\"\r\n");
            strBuf.append("Content-Type:application/octet-stream\r\n\r\n");
            out.writeBytes(strBuf.toString());
            System.out.println("send_file_data:" + strBuf.toString());
            fStream = new FileInputStream(filePath);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
			/* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
                out.write(buffer,0,length);
            }

            out.writeBytes("\r\n--"+boundary+"--\r\n");//文件上传结束
            System.out.println("\r\n--"+boundary+"--\r\n");
            isr = new InputStreamReader(httpConn.getInputStream(),"utf-8");
            br = new BufferedReader(isr);
            StringBuilder sbRet = new StringBuilder();
            String str;
            while((str = br.readLine())!=null){
                sbRet.append(str + "\r\n");
            }
            System.out.println("response_data:" + sbRet.toString());
            return sbRet.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(out!=null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(br!=null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(isr!=null)
                    isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fStream!=null){
                    fStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getTOTP(String url){
        Map params = new HashMap<>();

        Map serviceData = new HashMap<>();
        serviceData.put("otpPassKey","KNRUO42KIRUU4VDFKJTECY2FEE");

        Map postData = new HashMap<>();
        postData.put("service","getTOTPKey");
        postData.put("requestData",serviceData);

        //============请求参数map===============
        params.put("actionName","base-data-action");
        JSONObject jsonObject = new JSONObject(postData);
        params.put("postData", jsonObject.toString());

        System.out.println(params.toString());
        String response = doPost(url,params);
        JSONObject jsonObject1 = new JSONObject(response);
        Map otpMap = jsonObject1.toMap();

        return (String) otpMap.get("OTP");
    }
}
