package com.kyq.test.office.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 使用poi api对excel进行处理。生成对应的文档
 *  一页A4打印单元格，有59行，10列
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-03-07 11:31
 */
public class POIHelper {
    private HSSFFont titleFont = null;
    private HSSFFont contentFont = null;
    private HSSFCellStyle titleStyle = null;
    private HSSFCellStyle contentStyle = null;
    private HSSFCellStyle contentLabelStyle = null;
    private HSSFCellStyle contentRightLabelStyle = null;
    private Map<String, HSSFPatriarch> patriarchMap = new HashMap();
    public static void main(String args[]){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));

        System.out.println(year+month+day+hour+minute+second);

        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
        sf.format(new Date());
        System.out.println();


//        //获取文件源
//
//        try {
//            HSSFWorkbook workbook = new HSSFWorkbook();
//            HSSFSheet sheet = workbook.createSheet("sheet1");
//            FileOutputStream os = new FileOutputStream(new File("C:\\Users\\kyq1024\\Desktop\\work\\t4.xls"));
//            workbook.write(os);
//            os.flush();
//            os.close();
//            /*
//            FileInputStream fis = new FileInputStream("C:\\Users\\kyq1024\\Desktop\\work\\t3.xlsx");
//            //创建对Excel工作簿文件的引用
//            XSSFWorkbook workbook = new XSSFWorkbook(fis);
//            XSSFSheet sheet = workbook.getSheetAt(0);
//
//            XSSFRow row = sheet.getRow(0);
//            XSSFCell cell = row.getCell(0);
//            System.out.println("width:"+sheet.getColumnWidth(0));
//            System.out.println("height:"+row.getHeight());
//            */
//            /*
//            FileInputStream fis = new FileInputStream("C:\\Users\\kyq1024\\Desktop\\work\\t2.xls");
//            //创建对Excel工作簿文件的引用
//            HSSFWorkbook hsworkbook = new HSSFWorkbook(fis);
//            HSSFSheet hssheet = workbook.getSheetAt(0);
//
//            HSSFRow row = hssheet.getRow(0);
//            HSSFCell cell = row.getCell(0);
//            System.out.println("width:"+hssheet.getColumnWidth(0));
//            System.out.println("height:"+row.getHeight());
//            */
//        } catch (IOException e) {
//            e.printStackTrace();
//        }




//        new POIHelper().createExcel();
    }

    public void createExcel(){
        try {
            //创建一个excel文档，然后对它进行读写。
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("sheet1");
//            sheet.setDefaultColumnWidth(8.11);

            int currentRowIndex = 0;
            createSingleForm(currentRowIndex, workbook,sheet);

            currentRowIndex+=14;
            createSingleForm(currentRowIndex, workbook, sheet);

            currentRowIndex+=14;
            createSingleForm(currentRowIndex, workbook, sheet);

            currentRowIndex+=14;
            createSingleForm(currentRowIndex, workbook, sheet);

            FileOutputStream os = new FileOutputStream(new File("C:\\Users\\kyq1024\\Desktop\\work\\t1.xls"));
            workbook.write(os);
//            workbook.getBytes();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createSingleForm(int startRowIndex, HSSFWorkbook workbook,HSSFSheet sheet){

        //当前所在行指针

        //1.处理标题
        HSSFRow titleRow = sheet.createRow(startRowIndex+0);//创建标题行
        mergeSheetRegion(sheet,startRowIndex+0, startRowIndex+1, 0, 8); //1.合并单元格（0-1行，0-9列
        HSSFCell titleCell = titleRow.createCell(0);//设置头部单元格格式，插入内容：减免车辆信息确认单，字体：等线，加粗，14，居中
        titleCell.setCellStyle(getTitleStyle(workbook));//设置标题的样式，等线字体，加粗，14字号，水平居中，垂直居中
        titleCell.setCellValue("减免车辆信息确认单");

        //2.处理车辆信息,2,3行
        HSSFRow carRow1 = sheet.createRow(startRowIndex+2);//创建车辆信息行1
        HSSFCell timeLabelCell = carRow1.createCell(0);//时间标签
        timeLabelCell.setCellStyle(getContentLabelStyle(workbook));//设置内容标签样式：等线，10号字体，靠左对其，垂直居中
        timeLabelCell.setCellValue("时    间：");

        //1.合并单元格（2-2行，1-2列
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,
                mergeSheetRegion(sheet,startRowIndex+2, startRowIndex+2, 1, 2),sheet,workbook);
        HSSFCell timeContentCell = carRow1.createCell(1);//时间内容
        timeContentCell.setCellStyle(getContentStyle(workbook));//设置内容样式：等线，10号，水平居中，垂直居中
        timeContentCell.setCellValue("2.27 00:29");
        HSSFCell stationLabelCell = carRow1.createCell(3);//收费站标签
        stationLabelCell.setCellStyle(getContentLabelStyle(workbook));
        stationLabelCell.setCellValue("收费站：");

        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,
                mergeSheetRegion(sheet,startRowIndex+2, startRowIndex+2, 4, 5),sheet,workbook);//合并单元格（2-2行，4-5列)，设置底部边框线
        HSSFCell stationContentCell = carRow1.createCell(4);//收费站内容
        stationContentCell.setCellStyle(getContentStyle(workbook));
        stationContentCell.setCellValue("1920");

        HSSFCell plateLabelCell = carRow1.createCell(6);//车牌号标签
        plateLabelCell.setCellStyle(getContentLabelStyle(workbook));
        plateLabelCell.setCellValue("车牌号：");

        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,
                mergeSheetRegion(sheet,startRowIndex+2, startRowIndex+2, 7, 8),sheet,workbook);//合并单元格（2-2行，7-8列，设置底部边框线
        HSSFCell plateContentCell = carRow1.createCell(7);//车牌号内容
        plateContentCell.setCellStyle(getContentStyle(workbook));
        plateContentCell.setCellValue("渝A：330586");
        //创建车辆信息行2
        //--车牌颜色
        HSSFRow carRow2 = sheet.createRow(startRowIndex+3);
        HSSFCell plateColorCell = carRow2.createCell(0);//时间标签
        plateColorCell.setCellStyle(getContentLabelStyle(workbook));//设置内容标签样式：等线，10号字体，靠左对其，垂直居中
        plateColorCell.setCellValue("车牌颜色：");
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,
                mergeSheetRegion(sheet,startRowIndex+3, startRowIndex+3, 1, 2),sheet,workbook);//合并单元格（3-3行，1-2列，设置底部边框线
        HSSFCell plateColorContentCell = carRow2.createCell(1);//时间内容
        plateColorContentCell.setCellStyle(getContentStyle(workbook));//设置内容样式：等线，10号，水平居中，垂直居中
        plateColorContentCell.setCellValue("黄");
        //--轴型
        HSSFCell axleLabelCell = carRow2.createCell(3);//轴型标签
        axleLabelCell.setCellStyle(getContentLabelStyle(workbook));
        axleLabelCell.setCellValue("轴   型：");
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,
                mergeSheetRegion(sheet,startRowIndex+3, startRowIndex+3, 4, 5),sheet,workbook);//合并单元格（3-3行，4-5列，设置底部边框线
        HSSFCell axleContentCell = carRow2.createCell(4);//轴型内容
        axleContentCell.setCellStyle(getContentStyle(workbook));
        axleContentCell.setCellValue("1+2+3");
        //--吨位
        HSSFCell tonnageLabelCell = carRow2.createCell(6);//吨位标签
        tonnageLabelCell.setCellStyle(getContentLabelStyle(workbook));
        tonnageLabelCell.setCellValue("吨   位：");
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,
                mergeSheetRegion(sheet,startRowIndex+3, startRowIndex+3, 7, 8),sheet,workbook);//合并单元格（3-3行，7-8列，设置底部边框线
        HSSFCell tonnageContentCell = carRow2.createCell(7);//吨位内容
        tonnageContentCell.setCellStyle(getContentStyle(workbook));
        tonnageContentCell.setCellValue("48.48");

        //3.处理货物信息行,4,5,6行
        HSSFRow goodsRow1 = sheet.createRow(startRowIndex+4);
        mergeSheetRegion(sheet,startRowIndex+4, startRowIndex+4, 0, 2);//合并单元格0-2
        HSSFCell carType1 = goodsRow1.createCell(0);
        carType1.setCellStyle(getContentLabelStyle(workbook));
        carType1.setCellValue("☑鲜活农产品车辆      口混装");

        HSSFCell goodsLabelCell = goodsRow1.createCell(3);
        goodsLabelCell.setCellStyle(getContentLabelStyle(workbook));
        goodsLabelCell.setCellValue("运输产品：");
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,
                mergeSheetRegion(sheet,startRowIndex+4, startRowIndex+4, 4, 8),sheet,workbook);//合并单元格（3-3行，7-8列，设置底部边框线
        HSSFCell goodsContentCell = goodsRow1.createCell(4);
        goodsContentCell.setCellStyle(getContentStyle(workbook));
        goodsContentCell.setCellValue("萝卜");

        HSSFRow goodsRow2 = sheet.createRow(startRowIndex+5);
        mergeSheetRegion(sheet,startRowIndex+5, startRowIndex+5, 0, 2);//合并单元格0-2
        HSSFCell carType2 = goodsRow2.createCell(0);
        carType2.setCellStyle(getContentLabelStyle(workbook));
        carType2.setCellValue("口救灾车辆");

        HSSFCell materialLabelCell = goodsRow2.createCell(3);
        materialLabelCell.setCellStyle(getContentLabelStyle(workbook));
        materialLabelCell.setCellValue("运输物资：");
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,
                mergeSheetRegion(sheet,startRowIndex+5, startRowIndex+5, 4, 8),sheet,workbook);//合并单元格（5-5行，4-8列，设置底部边框线
        HSSFCell materialContentCell = goodsRow2.createCell(4);
        materialContentCell.setCellStyle(getContentStyle(workbook));


        //--
        HSSFRow goodsRow3 = sheet.createRow(startRowIndex+6);
        mergeSheetRegion(sheet,startRowIndex+6, startRowIndex+6, 0, 2);//合并单元格0-2
        HSSFCell carType3 = goodsRow3.createCell(0);
        carType3.setCellStyle(getContentLabelStyle(workbook));
        carType3.setCellValue("口其他");

        HSSFCell wareLabelCell = goodsRow3.createCell(3);
        wareLabelCell.setCellStyle(getContentLabelStyle(workbook));
        wareLabelCell.setCellValue("运输货物：");
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,
                mergeSheetRegion(sheet,startRowIndex+6, startRowIndex+6, 4, 8),sheet,workbook);//合并单元格（6-6行，4-8列，设置底部边框线
        HSSFCell wareContentCell = goodsRow3.createCell(4);
        wareContentCell.setCellStyle(getContentStyle(workbook));

        //3.责任
        HSSFRow dutyRow = sheet.createRow(startRowIndex+7);
        mergeSheetRegion(sheet,startRowIndex+7, startRowIndex+7, 0, 8);//合并单元格0-8
        HSSFCell dutyCell = dutyRow.createCell(0);
        dutyCell.setCellStyle(getContentLabelStyle(workbook));
        dutyCell.setCellValue("以上情况属实，本人所装载货物全部属减免范围，如有不符，愿意承担相关责任：");
        //4.空两行，创建签字栏
        sheet.createRow(startRowIndex+8);
        sheet.createRow(startRowIndex+9);

        //
        HSSFRow signRow = sheet.createRow(startRowIndex+10);

        HSSFCell tollLabel = signRow.createCell(0);
        tollLabel.setCellStyle(getContentLabelStyle(workbook));
        tollLabel.setCellValue("收费员：");
        //合并单元格
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,
                mergeSheetRegion(sheet,startRowIndex+8, startRowIndex+10, 1, 2),sheet,workbook);//合并单元格（6-6行，4-8列，设置底部边框线
        //在合并后的单元格放置一张图片
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            BufferedImage bufferImg = ImageIO.read(new File("C:\\Users\\kyq1024\\Desktop\\work\\sign1.png"));
            ImageIO.write(bufferImg, "png", byteArrayOut);
            HSSFClientAnchor anchor = new HSSFClientAnchor(2, 2, 1020, 245, (short)1 , startRowIndex+8, (short) 2, startRowIndex+10);
            anchor.setAnchorType(3);
            //插入图片
            getPatriarch(sheet).createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
            bufferImg.flush();
            byteArrayOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        HSSFCell carLabel = signRow.createCell(5);
        mergeSheetRegion(sheet,startRowIndex+10, startRowIndex+10, 5, 6);//合并单元格0-8
        carLabel.setCellStyle(getContentRightLabelStyle(workbook));//靠右
        carLabel.setCellValue("车方签字确认：");
        //合并单元格
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN,
                mergeSheetRegion(sheet,startRowIndex+8, startRowIndex+10, 7, 8),sheet,workbook);//合并单元格（10-10行，7-8列，设置底部边框线
        //在合并后的单元格放置一张图片
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            BufferedImage bufferImg = ImageIO.read(new File("C:\\Users\\kyq1024\\Desktop\\work\\sign2.png"));
            ImageIO.write(bufferImg, "png", byteArrayOut);
            HSSFClientAnchor anchor = new HSSFClientAnchor(2, 2, 1020, 245, (short)7 , startRowIndex+8, (short) 8, startRowIndex+10);
            anchor.setAnchorType(3);
            //插入图片
            getPatriarch(sheet).createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

            bufferImg.flush();
            byteArrayOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //=====
        RegionUtil.setBorderBottom(5, new CellRangeAddress(startRowIndex+10, startRowIndex+10, 0,8), sheet, workbook);//设置很粗的底部边框
        //授权row
        HSSFRow authRow = sheet.createRow(startRowIndex+11);
        HSSFCell authLabel = authRow.createCell(0);
        authLabel.setCellStyle(getContentLabelStyle(workbook));
        authLabel.setCellValue("授权人：");

        HSSFCell saveLabel = authRow.createCell(5);
        saveLabel.setCellStyle(getContentLabelStyle(workbook));
        saveLabel.setCellValue("高速公路救援、咨询、投诉电话：12122");
    }

    //创建标题单元格Font
    private HSSFFont getTitleFont(HSSFWorkbook workbook){
        if(titleFont==null){
            titleFont = workbook.createFont();
            titleFont.setFontName("等线");
            titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
            titleFont.setFontHeightInPoints((short) 14);  //字体大小
        }
        return titleFont;
    }

    //创建内容单元格Font
    private HSSFFont getContentFont(HSSFWorkbook workbook){
        if(this.contentFont==null){
            contentFont = workbook.createFont();
            contentFont.setFontName("等线");
            contentFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//不加粗
            contentFont.setFontHeightInPoints((short) 10);  //字体大小
        }
        return contentFont;
    }

    //创建标题样式
    private HSSFCellStyle getTitleStyle(HSSFWorkbook workbook){
        if(titleStyle == null){
            titleStyle = workbook.createCellStyle();
            titleStyle.setBorderBottom(HSSFCellStyle.BORDER_DASHED);//粗下边框,未成功
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
            titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
            titleStyle.setFont(getTitleFont(workbook));//选择需要用到的字体格式(标题Font)
        }
        return titleStyle;
    }
    private HSSFCellStyle getContentStyle(HSSFWorkbook workbook){
        if(contentStyle==null){
            contentStyle = workbook.createCellStyle();
            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
            contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
            contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//底部边框线
            contentStyle.setFont(getContentFont(workbook));//选择需要用到的字体格式(标题Font)
        }
        return contentStyle;
    }
    //创建内容标签样式
    private HSSFCellStyle getContentLabelStyle(HSSFWorkbook workbook){
        //垂直居中，左对齐
        if(contentLabelStyle ==null){
            contentLabelStyle = workbook.createCellStyle();
            contentLabelStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//左对齐
            contentLabelStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
            contentLabelStyle.setFont(getContentFont(workbook));//选择需要用到的字体格式(标题Font)
        }
        return contentLabelStyle;
    }
    //创建内容标签样式
    private HSSFCellStyle getContentRightLabelStyle(HSSFWorkbook workbook){
        //垂直居中，左对齐
        if(contentRightLabelStyle ==null){
            contentRightLabelStyle = workbook.createCellStyle();
            contentRightLabelStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//右对齐
            contentRightLabelStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
            contentRightLabelStyle.setFont(getContentFont(workbook));//选择需要用到的字体格式(标题Font)
        }
        return contentRightLabelStyle;
    }
    //获取画图管理器,一个sheet只能有一个
    private HSSFPatriarch getPatriarch(HSSFSheet sheet){
        if(!patriarchMap.containsKey(sheet.getSheetName())){
            patriarchMap.put(sheet.getSheetName(),sheet.createDrawingPatriarch());
        }
        return patriarchMap.get(sheet.getSheetName());
    }

    private CellRangeAddress mergeSheetRegion(HSSFSheet sheet,int firstRow, int lastRow, int firstCol, int lastCol){
        CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow, (short) firstCol, (short) lastCol);
        sheet.addMergedRegion(cellRangeAddress);
        return cellRangeAddress;
    }

}
