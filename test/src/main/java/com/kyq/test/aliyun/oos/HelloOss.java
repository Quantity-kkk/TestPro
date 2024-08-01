package com.kyq.test.aliyun.oos;

import com.aliyun.oss.*;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * Description:
 * 阿里云OSS sdk 使用Demo
 * maven 配置：
 *         <dependency>
 *             <groupId>com.aliyun.oss</groupId>
 *             <artifactId>aliyun-sdk-oss</artifactId>
 *             <version>3.5.0</version>
 *         </dependency>
 * Copyright: © 2019

 *
 * @author kyq
 * @version 1.0
 * @Date 2019/11/7 11:49
 */
public class HelloOss {

    /** OSS 管理界面获取的OSS 服务地址 */
    private static String endpoint = "http://oss-cn-xining-qhzyl-d01-a.ops.qhgs.com";
    /** 用户中心-部门管理里面获取的accessKey和accessKeySercret，根据官方文档建议，尽量不使用主账号的AccessKey，可以建立专门的OSS部门来达到对OSS AccessKey的管理 */
    private static String accessKeyId = "jOyADGJ4RkcF1zeV";
    private static String accessKeySecret = "9grsO8mdgQZxl11c7W2G9nRl1lTLzC";
    /** OSS 中建立的存储容器名称 */
    private static String bucketName = "dfs-charge";

    public static void main(String[] args){
        //线程安全的client，可以在实际应用中使用单例模式
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            if(ossClient.doesBucketExist(bucketName)){
                System.out.println("Bucket 已经创建！");
            }else {
                ossClient.createBucket(bucketName);
            }
            //获取bucket信息
            BucketInfo info = ossClient.getBucketInfo(bucketName);
            System.out.println("Bucket " + bucketName + "的信息如下：");
            System.out.println("\t数据中心：" + info.getBucket().getLocation());
            System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
            System.out.println("\t用户标志：" + info.getBucket().getOwner());

            //上传文件
            String key = UUID.randomUUID().toString();
            ossClient.putObject(bucketName, key,new File("D:\\workdocument\\杂文\\pic\\c212eda8jw1er0mxv59w5j20hs0hsq3w.jpg"));
            System.out.println("Object：" + key + "存入OSS成功。");
            //删除文件
            /*
                OSSObject object = ossClient.getObject(bucketName, key);
                InputStream objectContent = object.getObjectContent();
                ossClient.deleteObject(bucketName, key);
             */
            // Bucket设置私有情况下访问地址需要通过sdk获取: http://dfs-charge.oss-cn-xining-qhzyl-d01-a.ops.qhgs.com/025bf990-84ed-4a2e-b60b-ef2f65575296?Expires=1573107538&OSSAccessKeyId=jOyADGJ4RkcF1zeV&Signature=nqII7HdhAxV%2FAJ%2FqhgcF3CfuysM%3D
            // 设置公共读，通过 bucketName+endpoint+key 即可访问文件: http://dfs-charge.oss-cn-xining-qhzyl-d01-a.res.qhgs.com/025bf990-84ed-4a2e-b60b-ef2f65575296
            Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10 );
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(
                    bucketName, key, HttpMethod.GET);
            req.setExpiration(expiration);
            URL signedUrl = ossClient.generatePresignedUrl(req);
            System.out.println(signedUrl);
        }catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }
}
