package com.kyq.test.python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2017-12-21 14:03
 */
public class PythonInvoker {
    public static void main(String args[]){
        Process proc = null;
        try {
            System.out.println(System.getProperty("user.dir"));
            System.out.println(Class.class.getClass().getResource("/").getPath());
            String[] pythonArgs = new String[] { "python", "E:\\workspace\\python\\python\\learn\\image.py", "testargs","E:\\workspace\\python\\python\\learn\\image.py"};
            proc = Runtime.getRuntime().exec(pythonArgs);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
