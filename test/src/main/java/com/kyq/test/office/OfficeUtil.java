package com.kyq.test.office;

import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import org.jodconverter.LocalConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;

import java.io.File;
import java.io.IOException;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-01-18 13:47
 */
public class OfficeUtil {
    public static void main(String args[]){
        office2Pdf();
//        try {
//            t();
//        } catch (BootstrapException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public static void office2Pdf(){

            String sourceFile = "C:\\Users\\kyq1024\\Desktop\\work\\blogtmp\\test.docx";
            String desFile = "C:\\Users\\kyq1024\\Desktop\\work\\blogtmp\\convert.pdf";
            File inputFile = new File(sourceFile);
            if(!inputFile.exists()){//文件不存在
                System.out.println("file not exists!");
            }

            // 如果目标路径不存在, 则新建该路径
            File outputFile = new File(desFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }

            OfficeManager officeManager = null;
            try {
                officeManager = LocalOfficeManager.builder()
                        .portNumbers(8100)
                        .taskExecutionTimeout(60000)
                        .officeHome("C:\\Program Files (x86)\\OpenOffice 4")
                        .install()
                        .build();
                officeManager.start();


                LocalConverter.make()
                        .convert(inputFile)
                        .to(outputFile)
                        .execute();

//                officeManager.stop();

            } catch (OfficeException e) {
                e.printStackTrace();
            } finally {
//                if (officeManager != null) {
//                    try {
//                        officeManager.stop();
//                    } catch (OfficeException ex) {
//                        // Log the error...
//                    }
//                }
            }

//            JodConverter.convert(inputFile).to(outputFile).execute();

            /*
            //连接openoffice
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
            connection.connect();
            //转换文档
            DocumentConverter converter = new OpenOfficeDocumentConverter(
                    connection);
            converter.convert(inputFile, outputFile);

            // close the connection
            connection.disconnect();
            */


    }

    public static void t() throws BootstrapException, Exception, IOException {
        String sourceFile = "C:\\Users\\kyq1024\\Desktop\\work\\blogtmp\\数据绑定确认模块.docx";
        String desFile = "C:\\Users\\kyq1024\\Desktop\\work\\blogtmp\\convert.pdf";
        // get the remote office component context
        com.sun.star.uno.XComponentContext xContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
        // get the remote office service manager
        com.sun.star.lang.XMultiComponentFactory xMCF = xContext.getServiceManager();
        Object oDesktop = xMCF.createInstanceWithContext( "com.sun.star.frame.Desktop", xContext);
        com.sun.star.frame.XComponentLoader xCompLoader = (com.sun.star.frame.XComponentLoader)
        UnoRuntime.queryInterface( com.sun.star.frame.XComponentLoader.class, oDesktop);
        File file = new File(sourceFile);
        StringBuffer sLoadUrl = new StringBuffer("file:///");
        sLoadUrl.append(file.getCanonicalPath().replace(' ', '/'));
        file = new File(desFile);
        StringBuffer sSaveUrl = new StringBuffer("file:///");
        sSaveUrl.append(file.getCanonicalPath().replace(' ', '/'));
        com.sun.star.beans.PropertyValue[] propertyValue = new com.sun.star.beans.PropertyValue[1];
        propertyValue[0] = new com.sun.star.beans.PropertyValue();
        propertyValue[0].Name = "Hidden";
        propertyValue[0].Value = new Boolean(true);
        Object oDocToStore = xCompLoader.loadComponentFromURL( sLoadUrl.toString(), "_blank", 0, propertyValue );
        com.sun.star.frame.XStorable xStorable = (com.sun.star.frame.XStorable)UnoRuntime.queryInterface( com.sun.star.frame.XStorable.class, oDocToStore );
        propertyValue = new com.sun.star.beans.PropertyValue[ 2 ];
        propertyValue[0] = new com.sun.star.beans.PropertyValue();
        propertyValue[0].Name = "Overwrite";
        propertyValue[0].Value = new Boolean(true);
        propertyValue[1] = new com.sun.star.beans.PropertyValue();
        propertyValue[1].Name = "FilterName";
        propertyValue[1].Value = "writer_pdf_Export";
        xStorable.storeToURL( sSaveUrl.toString(), propertyValue );
        System.out.println("\nDocument \"" + sLoadUrl + "\" saved under \"" + sSaveUrl + "\"\n");
        com.sun.star.util.XCloseable xCloseable = (com.sun.star.util.XCloseable) UnoRuntime.queryInterface(com.sun.star.util.XCloseable.class, oDocToStore );
        if (xCloseable != null ) {
            xCloseable.close(false);
        }
    }
}
