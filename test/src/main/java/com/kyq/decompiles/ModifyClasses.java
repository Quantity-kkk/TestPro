package com.kyq.decompiles;

import javassist.*;

import java.io.IOException;

/**
 * Description: 修改class文件信息，破解RDP报表的水印问题
 * Copyright: © 2018

 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2019/5/5
 */
public class ModifyClasses {
    public static void main(String[] args) throws IOException{
        String filePath = "F:\\tmp\\rdp-core-1.2.jar";

        ClassPool pool = ClassPool.getDefault();
        try {
            pool.insertClassPath(filePath);
            CtClass cc1 = pool.get("com.pro.encryption.entrance.report.ReportEntrance");
            CtMethod method = cc1.getDeclaredMethod("auth");
            method.insertBefore("{if(true){" +
                    " com.pro.encryption.entrance.report.g v0001 = new com.pro.encryption.entrance.report.g();" +
                    "v0001.ALLATORIxDEMO(true);" +
                    "v0001.ALLATORIxDEMO(\"1\");" +
                    "v0001.M(\"01\");" +
                    "v0001.i(\"授权正常\");" +
                    "return v0001;} " +
                    "}");
            cc1.writeFile();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }
}
