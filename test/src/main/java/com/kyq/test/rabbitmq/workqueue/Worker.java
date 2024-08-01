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
public class Worker {
    private static final String TASK_QUEUE_NAME = "work_queue";

    public static void main(String args[]){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("kyq1024");
        factory.setPassword("kyq1024");
        factory.setHost("192.168.177.130");
//        factory.setConnectionTimeout(30000);
//        factory.setChannelRpcTimeout(30000);
        factory.setHandshakeTimeout(30000);
        try {
            final Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            System.out.println("Worker1 [*] Waiting for messages. To exit press CTRL+C");
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
                    System.out.println("Worker1 [x] Received '" + message + "'");

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
