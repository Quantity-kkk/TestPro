package com.kyq.test.sys;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.Enumeration;

/**
 * Description:
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/9/10
 */
public class EnvironmentHelper {
    private static final String OS_LINUX = "Linux";
    private static final String OS_WINDOWS = "Windows";

    private static final com.sun.management.OperatingSystemMXBean OPERATING_SYSTEM_MX_BEAN = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    /**
     * 获取本机IP地址
     * @return String
     * */
    public static String getHostIp(){
        InetAddress localInetAddress = getLocalInetAddress();
        if(localInetAddress!=null)
            return localInetAddress.getHostAddress();
        return null;
    }

    /**
     * 获取主机名称
     * @return String
     * */
    public static String getHostName(){
        InetAddress localInetAddress = getLocalInetAddress();
        if(localInetAddress!=null)
            return localInetAddress.getHostName();
        return null;
    }

    private static InetAddress getLocalInetAddress(){
        try{
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface =  allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":")==-1){//loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                        return ip;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
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
     * 获取当前电脑操作系统及版本信息
     * */
    public static String getSystemVersion(){
        return System.getProperty("os.name");
    }

    /**
     * 判断是否是linux，根据系统名字判断
     *
     * @return
     */
    public static boolean isLinux() {
        if (OS_WINDOWS.equals(getSystemType())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断是否是windows
     *
     * @return
     */
    public static boolean isWindows() {
        if (OS_LINUX.equals(getSystemType())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取磁盘大小
     *
     * @param isTotal       是否计算的是总大小 true--总大小   false--剩余大小
     * @param addSystemDisk 是否要计算系统盘 true--要计算  false--不计算
     * @return
     */
    public static String getDiskSize(boolean isTotal, boolean addSystemDisk) throws IOException {
        if (isWindows()) {
            // 遍历所有盘符
            float sum = 0;
            for (char diskFlag = 'A'; diskFlag <= 'Z'; diskFlag++) {
                String dirName = diskFlag + ":/";
                File win = new File(dirName);
                if (win.exists()) {
                    //判断是否要添加系统盘C盘
                    if (diskFlag == 'C' && !addSystemDisk) {
                        continue;
                    }
                    //总容量
                    if (isTotal) {
                        //总容量
                        sum += win.getTotalSpace();
                    } else {
                        //剩余容量
                        sum += win.getFreeSpace();
                    }
                }
            }
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format((float)sum / 1024 / 1024 / 1024);
        }
        /**
         * 规定 根目录 是系统盘
         *
         * 如果是linux系统总大小是所有挂在盘大小之和
         *
         * 例如：df -h
         * 文件系统                 容量  已用  可用 已用% 挂载点
         * /dev/mapper/centos-root   50G   31G   20G   62% /
         * devtmpfs                 3.8G     0  3.8G    0% /dev
         * tmpfs                    3.7G     0  3.7G    0% /dev/shm
         * tmpfs                    3.7G  162M  3.6G    5% /run
         * tmpfs                    3.7G     0  3.7G    0% /sys/fs/cgroup
         * /dev/mapper/centos-home  442G   24G  418G    6% /home
         * /dev/xvda1               497M  124M  373M   25% /boot
         * tmpfs                    756M     0  756M    0% /run/user/0
         */
        if (isLinux()) {
            //不用-h 便于手动计算,df得到的单位是K
            String[] allMsg = command("df -l").split("\n");
            float allSize = 0;
            if (isTotal && addSystemDisk) {
                for (String msg : allMsg) {
                    if (msg.indexOf("/") != -1) {//去掉标题行
                        allSize += Float.valueOf(getIndexMsg(msg, 2));
                    }
                }
                DecimalFormat df = new DecimalFormat("#.##");
                return df.format((float)allSize / 1024 / 1024) + "";
            }
            if (!isTotal && !addSystemDisk) {
                for (String msg : allMsg) {
                    if (msg.indexOf("/") != -1) {//去掉标题行
                        if (msg.endsWith("/")) {//!addSystemDisk除开系统盘
                            continue;
                        }
                        //第四列为可用(剩余)
                        allSize += Float.valueOf(getIndexMsg(msg, 4));
                    }
                }
                DecimalFormat df = new DecimalFormat("#.##");
                return df.format((float)allSize / 1024 / 1024);
            }
        }

        return "";
    }

    /**
     * 执行系统命令
     *
     * @param commandStr
     * @return
     */
    public static String command(String commandStr) throws IOException {
        StringBuilder msg = new StringBuilder();
        Process pro = Runtime.getRuntime().exec(commandStr);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream(), "gbk"));
        String temp;
        while ((temp = bufferedReader.readLine()) != null) {
            msg.append(temp);
            msg.append("\n");
        }
        return msg.toString();
    }

    public static String getIndexMsg(String msgStr, int index) {
        if (msgStr != null && !msgStr.equals("")) {
            String[] ms = msgStr.split(" ");
            int count = 1;
            for (String msg : ms) {
                if ("".equals(msg)) {
                    continue;
                } else {
                    if (count == index) {
                        return msg;
                    }
                    count++;
                }
            }
        }
        return null;
    }
    public static String getCpuUsage(){
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(OPERATING_SYSTEM_MX_BEAN.getProcessCpuLoad() * 100) ;
    }
}
