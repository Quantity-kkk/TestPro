package com.kyq.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Description： com.kyq.tools
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-06-06 17:31
 */
public class FileScan {
    /**
     * 扫描超过1000行的java文件
     * */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        add(new File("D:\\work\\2023\\0512吉林省中心计费\\code\\station-reids\\src\\main\\java"));
        long endTime = System.currentTimeMillis();
        System.out.println("统计耗时时:" + (endTime - startTime) + "毫秒");
    }
    public static void add(File file) {
        if (file.isDirectory()) {
            for (File file1 :file.listFiles()) {
                add(file1);
            }
        }
        if (file.isFile()) {
            try {
                List<String> imgs =new ArrayList<String>();
                imgs.add("JAVA");
                imgs.add("java");
                if (null !=getFileSuffix(file) &&imgs.contains(getFileSuffix(file))) {
                    long lines = Files.lines(Paths.get(file.getPath())).count();
                    if (lines >1000) {
                        System.out.println(file.getPath() +"\\" +file.getName() +"文件总共有 : " +lines +"行");
                    }
                }
            } catch (IOException e) {
                System.out.println("发生异常");
            }
        }
    }
    public static String getFileSuffix(File file) {
        if (file ==null) {
            return null;
        }
        String suffix =null;
        String fileName =file.getName();
        if (fileName.lastIndexOf(".") != -1 &&fileName.lastIndexOf(".") !=0) {
            suffix =fileName.substring(fileName.lastIndexOf(".") +1);
        }
        return suffix;
    }
}
