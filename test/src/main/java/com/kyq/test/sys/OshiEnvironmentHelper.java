package com.kyq.test.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Description:
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/9/10
 */
public class OshiEnvironmentHelper {
    Logger logger = LoggerFactory.getLogger(OshiEnvironmentHelper.class);

    private static final String OS_LINUX = "Linux";
    private static final String OS_WINDOWS = "Windows";

    public static void main(String args[]){
        System.out.println(getHostName(getHostIp()));
    }

    public static void hardWare(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        hal.getProcessor().getSystemCpuLoad();
//        PlatformEnum currentPlatformEnum = SystemInfo.getCurrentPlatformEnum();
//        System.out.println(currentPlatformEnum);
//        System.out.println(si.getOperatingSystem());
    }
    /**
     * 根据文件分隔符来获取当前电脑操作系统类型,Windows和linux通用
     * */
    public static String getSystemType(){
        if("\\".equals(File.separator)){
            return OS_WINDOWS;
        }else {
            return OS_LINUX;
        }
    }

    /**
     * 获取主机IP地址
     * */
    public static String getHostIp(){
        //直接获取配置的IP地址
        return "10.17.31.108";
    }

    /**
     * 获取主机名称
     * */
    public static String getHostName(String ip){
        String[] ipStr=ip.split("[.]");
        byte[] ipBytes=new byte[4];
        for (int i = 0; i < 4; i++) {
            int m=Integer.parseInt(ipStr[i]);
            byte b=(byte)(m&0xff);
            ipBytes[i]=b;
        }
        try {
            InetAddress byAddress = InetAddress.getByAddress(ipBytes);
            return byAddress.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取计算机CPU信息
     * */


}
