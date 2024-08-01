package com.kyq.test.fitpath;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Copyright: © 2019

 *
 * @author kyq
 * @version 1.0
 * @Date 2019/12/22 12:14
 */
public class ParamProcess {
    private static int bufSize = 1024;
    private byte key = "\n".getBytes()[0];
    private String encode = "UTF-8";

//    private List<String> params = new ArrayList<>();

    public List<String> readFileByLine() throws IOException {
        boolean saveNextLine = false;
        List<String> params = new ArrayList<>();
        File fin = new File("D:\\workdocument\\杂文\\tmp\\stations.log");
        FileChannel fcin = new RandomAccessFile(fin, "r").getChannel();
        try {
            ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);
            // 每次读取的内容
            byte[] bs = new byte[bufSize];
            // 缓存
            byte[] tempBs = new byte[0];
            String line = "";
            while (fcin.read(rBuffer) != -1) {
                int rSize = rBuffer.position();
                rBuffer.rewind();
                rBuffer.get(bs);
                rBuffer.clear();
                byte[] newStrByte = bs;
                // 如果发现有上次未读完的缓存,则将它加到当前读取的内容前面
                if (null != tempBs) {
                    int tL = tempBs.length;
                    newStrByte = new byte[rSize + tL];
                    System.arraycopy(tempBs, 0, newStrByte, 0, tL);
                    System.arraycopy(bs, 0, newStrByte, tL, rSize);
                }
                int fromIndex = 0;
                int endIndex = 0;
                // 每次读一行内容，以 key（默认为\n） 作为结束符
                while ((endIndex = indexOf(newStrByte, fromIndex)) != -1) {
                    byte[] bLine = substring(newStrByte, fromIndex, endIndex);
                    line = new String(bLine, 0, bLine.length, encode);
                    // 输出一行内容，处理方式由调用方提供
                    if(saveNextLine){
                        params.add(line);
                        saveNextLine = false;
                    }

                    if(line.contains("call fit path") && !line.contains("result")){
                        //下一行需要保存
                        saveNextLine = true;
                    }

                    fromIndex = endIndex + 1;
                }
                // 将未读取完成的内容放到缓存中
                tempBs = substring(newStrByte, fromIndex, newStrByte.length);
            }
            // 将剩下的最后内容作为一行，输出，并指明这是最后一行
//            String lineStr = new String(tempBs, 0, tempBs.length, encode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fcin.close();
        }
        return params;
    }

    public static void main(String[] args) throws IOException {
        ParamProcess process = new ParamProcess();
        List<String> strings = process.readFileByLine();
        System.out.println(strings.size());
    }



    /**
     * 从指定开始位置读取一个byte[]直到指定结束位置为止生成一个全新的byte[]
     * @param src
     * @param fromIndex
     * @param endIndex
     * @return
     * @throws Exception
     */
    private byte[] substring(byte[] src, int fromIndex, int endIndex) throws Exception {
        int size = endIndex - fromIndex;
        byte[] ret = new byte[size];
        System.arraycopy(src, fromIndex, ret, 0, size);
        return ret;
    }

    private int indexOf(byte[] src, int fromIndex){
        for (int i = fromIndex; i < src.length; i++) {
            if (src[i] == key) {
                return i;
            }
        }
        return -1;
    }
}
