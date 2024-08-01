package com.kyq.test.schema;

import com.jfinal.kit.Okv;
import com.jfinal.kit.StrKit;
import com.kyq.test.util.StringUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

/**
 * @author source
 * @date 2019/9/21 11:09
 */
@SuppressWarnings("Duplicates")
public class GeneratorSchema {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorSchema.class);

    private Path basePath;
    private Path reqPath;
    private Path resPath;
    private Map<String, String> oldNameMap;
    private int oldNameCount = 0;
    private XSSFWorkbook xssfWorkbook;


    public GeneratorSchema(String xlsPath, String basePath) throws IOException {
        this(new FileInputStream(xlsPath), Objects.requireNonNull(basePath), new HashMap<>());
    }

    public GeneratorSchema(String xlsPath, String basePath, Map<String, String> oldNameMap) throws IOException {
        this(new FileInputStream(xlsPath), Objects.requireNonNull(basePath), oldNameMap);
    }

    public GeneratorSchema(FileInputStream xlsInputStream, String basePath) throws IOException {
        this(xlsInputStream, Objects.requireNonNull(basePath), new HashMap<>());
    }


    public GeneratorSchema(FileInputStream xlsInputStream, String basePath, Map<String, String> oldNameMap) throws IOException {
        this.basePath = Paths.get(basePath);
        this.reqPath = this.basePath;
        this.resPath = this.basePath;
//        this.reqPath = this.basePath.resolve("req");
//        this.resPath = this.basePath.resolve("res");
        this.oldNameMap = oldNameMap;
        //1.读取Excel文档对象
        xssfWorkbook = new XSSFWorkbook(xlsInputStream);
    }

    public void excelToReq() {
        //2.获取要解析的表格（第一个表格）
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        //获得最后一行的行号
        int lastRowNum = sheet.getLastRowNum();
        boolean start = false;
        String reqFileName = null;
        Map<String, Okv> tableKv = new HashMap<>(16);
        Okv curentkv = null;
        String lastRowText;
        boolean nonUse = false;
        StringBuilder rowText = new StringBuilder();
        List<Integer> skipList = Arrays.asList(0, 0, 0, 0);
        //遍历每一行
        for (int i = 0; i <= lastRowNum; i++) {
            try {
                //3.获得要解析的行
                XSSFRow row = sheet.getRow(i);
                lastRowText = rowText.toString();
                rowText = new StringBuilder();
                Iterator<Cell> iterator = row.cellIterator();
                while (iterator.hasNext()) {
                    String text = getText(iterator);
                    rowText.append(text.trim());
                    if (text.contains("（停用）")) {
                        nonUse = true;
                        logger.error("跳过接口 {}", text);
                    }

                    if (!StrKit.isBlank(reqFileName) && StringUtil.isMatchCaseInsensitiveIn("文件内容|请求内容", text)) {
                        if (!nonUse) {
                            curentkv = beginParse(reqFileName, tableKv);
                            start = true;
                        } else {
                            nonUse = false;
                            reqFileName = null;
                            curentkv = null;
                            tableKv.clear();
                        }
                    }
                    if (start && text.contains("响应文件名")) {
                        start = false;
                        processOkv(tableKv.get("root"));
                        if (curentkv == null) {
                            logger.error("curentkv 异常， 请检查错误 ");
                        }
                        //logger.info(rootkv.toJson());
                        String jsonString = StringUtil.formatJsonStr(tableKv.get("root").toJson());
                        try {
                            Files.write(reqPath.resolve(reqFileName), jsonString.getBytes(Charset.forName("utf-8")), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                        } catch (Exception e) {
                            logger.error("生成文件异常 {}", e.getMessage());
                            logger.error("异常文件名 {}, {}", reqPath, reqFileName);
                        }
                        logger.info("生成文件...{}", reqFileName);
                        reqFileName = null;
                        curentkv = null;
                        tableKv.clear();
                    }

                    if (text.contains("_REQ_")) {
                        reqFileName = text.substring(0, text.indexOf("_REQ_") + 4);
                        reqFileName = resetReqFileName(reqFileName);
                        break;
                    } else {
                        if (!start || text.contains("文件内容")) {
                            break;
                        }

                        //属性,描述,类型,规则,必填
                        if ("属性".equals(text) || "[".equals(text)) {
                            int skip = 0;
                            int index = 0;
                            while (iterator.hasNext()) {
                                String string = iterator.next().getStringCellValue();
                                if (StrKit.isBlank(string)) {
                                    skip++;
                                } else {
                                    skipList.set(index, skip);
                                    index++;
                                    skip = 0;
                                }
                            }

                            if (StrKit.notBlank(lastRowText)) {
                                if (lastRowText.contains("文件内容")) {
                                    curentkv = tableKv.get("root");
                                } else {
                                    for (String key : tableKv.keySet()) {
                                        if (lastRowText.toUpperCase().contains(key.toUpperCase())) {
                                            curentkv = tableKv.get(key);
                                        }
                                    }
                                }
                            }
                            break;
                        }

                        if (StrKit.notBlank(text)) {
                            if (!StringUtil.isMatchCaseInsensitiveIn("[a-zA-Z0-9_]+", text)) {
                                break;
                            }
                            Okv kv = Okv.create();
                            kv.set("id", curentkv.get("path") + text);

                            getTextBlank(iterator, skipList.get(0));
                            String title = getText(iterator);
                            if (!iterator.hasNext() || StrKit.isBlank(title)) {
                                break;
                            }
                            kv.set("title", title);
                            getTextBlank(iterator, skipList.get(1));
                            String type = getText(iterator);
                            kv.set("type", StrKit.isBlank(type) ? "array" : type);
                            getTextBlank(iterator, skipList.get(2));
                            String description = getText(iterator);
                            kv.set("description", StrKit.isBlank(description) ? title : description);
                            getTextBlank(iterator, skipList.get(3));
                            Okv properties = curentkv.getAs("properties");
                            ArrayList<String> strings = curentkv.getAs("required");
                            String required = getText(iterator);
                            if ("是".equals(required)) {
                                strings.add(text);
                            }
                            if (properties.containsKey(text)) {
                                logger.error("异常properties {},{},{}", text, kv.get("id"), reqFileName);
                            } else {
                                properties.set(text, kv);
                            }

                            if ("array".equals(kv.get("type"))) {
                                String itemPath = curentkv.get("path") + text + "/items";
                                String path = itemPath + "/properties/";
                                kv.set("items",
                                        Okv.by("id", itemPath)
                                                .set("type", "object")
                                                .set("path", path)
                                                .set("properties", Okv.create())
                                                .set("required", new ArrayList()));
                                tableKv.put(text, (Okv) kv.get("items"));
                                logger.info("{} 存在数组类型 {}", reqFileName, text);
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                start = false;
                t.printStackTrace();
            }
        }
    }

    public void excelToRes() {

        //2.获取要解析的表格（第一个表格）
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        //获得最后一行的行号
        int lastRowNum = sheet.getLastRowNum();
        boolean start = false;
        String reqFileName = null;
        Map<String, Okv> tableKv = new HashMap<>(16);
        Okv curentkv = null;
        boolean nonUse = false;
        String lastRowText;
        StringBuilder rowText = new StringBuilder();
        List<Integer> skipList = Arrays.asList(0, 0, 0, 0);
        //遍历每一行
        for (int i = 0; i <= lastRowNum; i++) {
            try {
                //3.获得要解析的行
                XSSFRow row = sheet.getRow(i);
                lastRowText = rowText.toString();
                rowText = new StringBuilder();
                Iterator<Cell> iterator = row.cellIterator();
                while (iterator.hasNext()) {
                    String text = getText(iterator);
                    rowText.append(text.trim());
                    if (text.contains("（停用）")) {
                        nonUse = true;
                        logger.error("跳过接口 {}", text);
                    }
                    if (!StrKit.isBlank(reqFileName) && StringUtil.isMatchCaseInsensitiveIn("文件内容|响应内容", text)) {
                        if (!nonUse) {
                            curentkv = beginParse(reqFileName, tableKv);
                            start = true;
                        } else {
                            nonUse = false;
                            reqFileName = null;
                            curentkv = null;
                            tableKv.clear();
                        }
                    }
                    if (start && text.contains("请求文件名")) {
                        start = false;
                        processOkv(tableKv.get("root"));
                        if (curentkv == null) {
                            logger.error("curentkv 异常， 请检查错误 ");
                        }
                        //logger.info(rootkv.toJson());
                        String jsonString = StringUtil.formatJsonStr(tableKv.get("root").toJson());
                        try {
                            Files.write(resPath.resolve(reqFileName), jsonString.getBytes(Charset.forName("utf-8")), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                        } catch (Exception e) {
                            logger.error("生成文件异常 {}", e.getMessage());
                            logger.error("异常文件名 {}, {}", resPath, reqFileName);
                        }
                        logger.info("生成文件...{}", reqFileName);
                        reqFileName = null;
                        curentkv = null;
                        tableKv.clear();
                    }


                    if (text.contains("_RES_")) {
                        reqFileName = text.substring(0, text.indexOf("_RES_") + 4);
                        reqFileName = resetReqFileName(reqFileName);
                        break;
                    } else {
                        if (!start || text.contains("文件内容")) {
                            break;
                        }

                        //属性,描述,类型,规则,必填
                        if ("属性".equals(text) || "[".equals(text)) {
                            int skip = 0;
                            int index = 0;
                            while (iterator.hasNext()) {
                                String string = iterator.next().getStringCellValue();
                                if (StrKit.isBlank(string)) {
                                    skip++;
                                } else {
                                    skipList.set(index, skip);
                                    index++;
                                    skip = 0;
                                }
                            }

                            if (StrKit.notBlank(lastRowText)) {
                                if (lastRowText.contains("文件内容")) {
                                    curentkv = tableKv.get("root");
                                } else {
                                    for (String key : tableKv.keySet()) {
                                        if (lastRowText.toUpperCase().contains(key.toUpperCase())) {
                                            curentkv = tableKv.get(key);
                                        }
//                                        if ("[".equals(key)) {
//                                            curentkv = tableKv.get(lastRowText);
//                                        }
                                    }
                                }
                            }
                            break;
                        }

                        if (StrKit.notBlank(text)) {
                            if (!StringUtil.isMatchCaseInsensitiveIn("[a-zA-Z0-9_]+", text)) {
                                break;
                            }
                            Okv kv = Okv.create();
                            kv.set("id", curentkv.get("path") + text);

                            getTextBlank(iterator, skipList.get(0));
                            String title = getText(iterator);
                            if (!iterator.hasNext() || StrKit.isBlank(title)) {
                                break;
                            }
                            kv.set("title", title);
                            getTextBlank(iterator, skipList.get(1));
                            String type = getText(iterator);
                            kv.set("type", StrKit.isBlank(type) ? "array" : type);
                            getTextBlank(iterator, skipList.get(2));
                            String description = getText(iterator);
                            kv.set("description", StrKit.isBlank(description) ? title : description);
                            getTextBlank(iterator, skipList.get(3));
                            Okv properties = curentkv.getAs("properties");
                            ArrayList<String> strings = curentkv.getAs("required");
                            String required = getText(iterator);
                            if ("是".equals(required)) {
                                strings.add(text);
                            }
                            if (properties.containsKey(text)) {
                                logger.error("异常properties {},{},{}", text, kv.get("id"), reqFileName);
                            } else {
                                properties.set(text, kv);
                            }

                            if ("array".equals(kv.get("type"))) {
                                String itemPath = curentkv.get("path") + text + "/items";
                                String path = itemPath + "/properties/";
                                kv.set("items",
                                        Okv.by("id", itemPath)
                                                .set("type", "object")
                                                .set("path", path)
                                                .set("properties", Okv.create())
                                                .set("required", new ArrayList()));
                                tableKv.put(text, (Okv) kv.get("items"));
                                logger.info("{} 存在数组类型 {}", reqFileName, text);
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                start = false;
                t.printStackTrace();
            }
        }
    }

    public void interfaceMapping() throws IOException {
        List<Okv> beanArray = new ArrayList<>();
        try (DirectoryStream<Path> files = Files.newDirectoryStream(reqPath, "*_REQ")) {
            for (Path file : files) {
                String fileName = file.toFile().getName();
                beanArray.add(Okv.by("bean", fileName)
                        .set("beanReqName", fileName + "_SENDER_yyyyMMddHHmmssSSS.json")
                        .set("beanReqRegex", "^" + fileName + "_${senderRegex}_${timeRegex}.(json|zip)$")
                        .set("beanResName", fileName.replace("_REQ", "_RES") + "_SENDER_yyyyMMddHHmmssSSS.json")
                        .set("beanResRegex", "^" + fileName.replace("_REQ", "_RES") + "_${senderRegex}_${timeRegex}.(json|zip)$"));
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        try (DirectoryStream<Path> files = Files.newDirectoryStream(resPath, "*_RES")) {
            for (Path file : files) {
                String fileName = file.toFile().getName();
                beanArray.add(Okv.by("bean", fileName)
                        .set("beanReqName", fileName + "_SENDER_yyyyMMddHHmmssSSS.json")
                        .set("beanReqRegex", "^" + fileName + "_${senderRegex}_${timeRegex}.(json|zip)$")
                        .set("beanResName", fileName.replace("_REQ", "_RES") + "_SENDER_yyyyMMddHHmmssSSS.json")
                        .set("beanResRegex", "^" + fileName.replace("_REQ", "_RES") + "_${senderRegex}_${timeRegex}.(json|zip)$"));
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        Okv rootkv = Okv.create();
        rootkv.set("timeRegex", "((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229))(0\\d{1}|1\\d{1}|2[0-3])[0-5]\\d{1}([0-5]\\d{1})[0-9]{3}")
                .set("senderRegex", "[0-9]+")
                .set("provinceSenderRegex", "[0-9]+")
                .set("beanArray", beanArray);
        System.out.println(rootkv.toJson());
        String jsonString = StringUtil.formatJsonStr(rootkv.toJson());
        Files.write(basePath.resolve("interfaceMapping"), jsonString.getBytes(Charset.forName("utf-8")), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }


    private String getText(Iterator<Cell> iterator) {
        if (!iterator.hasNext()) {
            return "";
        }
        String string;
        try {
            string = iterator.next().getStringCellValue();
        } catch (Exception e) {
            return "";
        }
        return string.replaceAll("[\\s]", "").replaceAll("“", "(").replaceAll("”", ")");
    }

    private String resetReqFileName(String reqFileName) {
        String old = reqFileName;
        reqFileName = reqFileName.replace("_REQ", "_REQUEST");
        reqFileName = reqFileName.replace("_", "");
        if (oldNameMap.containsKey(reqFileName)) {
            oldNameCount++;
            logger.info("find old schema {}, {}", oldNameMap.get(reqFileName), oldNameCount);
            return oldNameMap.get(reqFileName);
        }
        return old;
    }


    private Okv beginParse(String reqFileName, Map<String, Okv> tableKv) {
        logger.info("开始解析文件...{}", reqFileName);
//        if ("TRANSACTION_ETCEXTU_REQ".equals(reqFileName)) {
//            logger.info("ssss");
//        }
        Okv curentkv = Okv.create();

        tableKv.put("root", curentkv);
        curentkv.set("$schema", "http://json-schema.org/draft-04/schema#")
                .set("id", "http://dsdas.com/" + reqFileName + ".json")
                .set("type", "object")
                .set("properties", Okv.create())
                .set("path", "/properties/")
                .set("required", new ArrayList<String>());
        return curentkv;
    }


    /**
     * 处理中间数据
     *
     * @param kv
     */
    private void processOkv(Okv kv) {
        if (Objects.isNull(kv)) {
            return;
        } else if (kv.notNull("path")) {
            kv.remove("path");
        } else if (kv.notNull("properties")) {
            kv.set("type", "object");
        } else {
            //匹配替换校验规则
            boolean processFlag = false;
            for (PropertiesEnum propertiesEnum : PropertiesEnum.values()) {
                processFlag = StringUtil.isMatchCaseInsensitiveIn(propertiesEnum.getId(), kv.getStr("id")) &&
                        StringUtil.isMatchCaseInsensitiveIn(propertiesEnum.getType(), kv.getStr("type")) &&
                        StringUtil.isMatchCaseInsensitiveIn(propertiesEnum.getTitle(), kv.getStr("title")) &&
                        StringUtil.isMatchCaseInsensitiveIn(propertiesEnum.getDescription(), kv.getStr("description"));
                if (processFlag) {
                    propertiesEnum.getConsumer().accept(kv);
                    break;
                }
            }
            if (!processFlag) {
                logger.error("未找到对应的处理逻辑{}", kv.getStr("id"));
            }
        }

        Set<Map.Entry> entrySet = kv.entrySet();
        for (Map.Entry entry : entrySet) {
            if (entry.getValue() instanceof Okv) {
                processOkv((Okv) entry.getValue());
            }
        }
    }


    private void getTextBlank(Iterator<Cell> iterator, int count) {
        for (int i = 0; i < count && iterator.hasNext(); i++) {
            iterator.next();
        }
    }

}
