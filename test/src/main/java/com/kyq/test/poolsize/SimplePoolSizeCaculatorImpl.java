package com.kyq.test.poolsize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description：
 * 继承这个抽象类PoolSizeCalculator并实现它的三个抽象方法，
 * 比如下面一个示例（任务是请求网络数据），其
 * 中我指定期望CPU利用率为1.0（即100%），
 * 任务队列总大小不超过100,000字节
 *
 * @author Will
 */
public class SimplePoolSizeCaculatorImpl extends PoolSizeCalculator{
    @Override
    protected Runnable creatTask() {
        return new AsyncIoTask();
    }

    @Override
    protected BlockingQueue createWorkQueue() {
        return new LinkedBlockingQueue(1000);
    }

    @Override
    protected long getCurrentThreadCPUTime() {
        return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
    }

    public static void main(String[] args) {
        PoolSizeCalculator poolSizeCalculator = new SimplePoolSizeCaculatorImpl();
        poolSizeCalculator.calculateBoundaries(new BigDecimal(1.0), new BigDecimal(100000));
    }

    /**
     * 自定义的异步IO任务
     * */
    class AsyncIoTask implements Runnable{
        @Override
        public void run() {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                String getURL = "http://baidu.com";
                URL getUrl = new URL(getURL);

                connection = (HttpURLConnection) getUrl.openConnection();
                connection.connect();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {

                }
            } catch (IOException e) {

            } finally {
                if(reader != null) {
                    try {
                        reader.close();
                    }
                    catch(Exception e) {

                    }
                }
                connection.disconnect();
            }
        }
    }
}
