package com.kyq.test.rabbitmq.workqueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-03-29 16:50
 */
public class Worker2 {
    private static final String TASK_QUEUE_NAME = "work_queue";

    public static void main(String args[]){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("kyq1024");
        factory.setPassword("kyq1024");
        factory.setHost("192.168.177.130");
        factory.setHandshakeTimeout(30000);//设置握手超时时间,虚拟机响应经常超时。
        try {
            final Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            System.out.println("Worker2 [*] Waiting for messages. To exit press CTRL+C");
            //绑定队列，通过键 TASK_QUEUE_NAME 将队列和交换器绑定起来
//            channel.queueBind("aaa", "fanout_exchange", TASK_QUEUE_NAME);
            // 每次从队列中获取数量
            channel.basicQos(1);
            channel.basicConsume(TASK_QUEUE_NAME, false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");

                    try {
                        Thread.sleep(1000); // 暂停1秒钟
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("Worker2 [x] Received '" + message + "'");

                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
