package com.kyq.test.sys;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Description:
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/9/10
 */
public class MonitorTest {
    public static void main(String args[]) throws IOException {
//        String keyWord = "chrome.exe";
//        findWindowsProcess(keyWord);
//        showSystemInfo();

//        System.out.println(EnvironmentHelper.getDiskSize(true,true));
//        System.out.println(EnvironmentHelper.getHostName());
//        System.out.println(execWindowsCommand("cmd /c ver"));
        System.out.println(EnvironmentHelper.getCpuUsage());

    }

    public static String getLocalIp(){
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            String ip=addr.getHostAddress(); //获取本机ip
            return ip;
//            String hostName=addr.getHostName().toString(); //获取本机计算机名称
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getHostIp(){
        try{
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":")==-1){
                        return ip.getHostAddress();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取当前电脑操作系统类型
     * */
    public static String getSystemType(){
        if("\\".equals(File.separator)){
            return "Windows";
        }else {
            return "Linux";
        }
    }

    public static void findWindowsProcess(String processName){
        String keyWord = "chrome.exe";
        Runtime runtime = Runtime.getRuntime();

        try{
            Process process = runtime.exec("cmd /c Tasklist");

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while ((s = in.readLine())!=null){
                s = s.toLowerCase();
                if(s.startsWith(keyWord)){
                    System.out.println("=============>"+s);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showSystemInfo(){
        Properties props = System.getProperties();
        //获取系统版本，目前的有的JDK1.8版本会将win10也标识为win8.1。
        String osName = props.getProperty("os.name");
        String osArch = props.getProperty("os.arch");
        String osVersion = props.getProperty("os.version");
        System.out.println(osName);
        System.out.println(osArch);
        System.out.println(osVersion);
    }

    //执行命令
    public static String execWindowsCommand(String command){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(command);

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = in.readLine())!=null){
                if(sb.length()>0){
                    sb.append("/r/n");
                }
                sb.append(s);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
