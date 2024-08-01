package com.kyq.test.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-03-29 16:44
 */
public class NewTask {
    private static final String TASK_QUEUE_NAME = "work_queue";

    public static void main(String args[]){
        defaultExchange();
    }

    public static void defaultExchange(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("kyq1024");
        factory.setPassword("kyq1024");
        factory.setHost("192.168.177.130");
//        factory.setConnectionTimeout(30000);
//        factory.setChannelRpcTimeout(30000);
        factory.setHandshakeTimeout(20000);//设置握手超时时间,虚拟机响应经常超时。
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            for(int i = 0; i<5; i++){
                String message = "Hello World!_" + i;
                channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
    public static void fanoutExchange(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("kyq1024");
        factory.setPassword("kyq1024");
        factory.setHost("192.168.177.130");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //关于fanoutExchange，只需要队列绑定到该交换机上，就可以接收消息，不需要routeKey
            channel.exchangeDeclare("fanout_exchange", "fanout");
//            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            for(int i = 0; i<5; i++){
                String message = "Hello World!_" + i;
                channel.basicPublish("fanout_exchange", "", null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
