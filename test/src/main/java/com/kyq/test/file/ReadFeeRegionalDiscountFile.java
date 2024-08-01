package com.kyq.test.file;

import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReadFeeRegionalDiscountFile {

    public static void main(String[] args) throws IOException {
        String fileDir = "C:\\Users\\Lenovo\\Documents\\WXWork\\1688854817342061\\Cache\\File\\2024-01\\FPREGIONALDISCOUNT.TXT";

        File file = new File(fileDir);
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> fileContentList = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null){
            if(line!=null && !"".equals(line.trim())){
                fileContentList.add(line.trim());
            }
        }
        List<String> colName = new ArrayList<>();
        String colLine = fileContentList.get(0);
        String[] colArr = colLine.split(",");
        colName.addAll(Arrays.stream(colArr).collect(Collectors.toList()));
        for( int i = 1; i < fileContentList.size(); i++){
            line = fileContentList.get(i);
            String[] valArr = line.split(",");
            StringBuilder sqlBuilder = new StringBuilder("insert into fee_regional_discount(");
            StringBuilder valBuilder = new StringBuilder(" values(");
            for (int j = 0 ; j < valArr.length; j++) {
                sqlBuilder.append(StrUtil.toUnderlineCase(colArr[j])).append(",");
                valBuilder.append("'").append(valArr[j]).append("'").append(",");
            }
            sqlBuilder.delete(sqlBuilder.length()-1,sqlBuilder.length());
            sqlBuilder.append(")");
            valBuilder.delete(valBuilder.length()-1,valBuilder.length());
            valBuilder.append(");");
            System.out.println(sqlBuilder.toString()+valBuilder.toString());
        }
    }
}
