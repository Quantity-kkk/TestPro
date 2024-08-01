package com.kyq.test.rabbitmq;

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
 * @timestamp: 2018-03-27 16:02
 */
public class Producer {
    public static void main(String args[]){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("kyq1024");
        connectionFactory.setPassword("kyq1024");
        connectionFactory.setHost("192.168.177.130");
        connectionFactory.setHandshakeTimeout(15000);//设置握手超时时间,虚拟机响应经常超时。
        try {
            Connection conn = connectionFactory.newConnection();
            //获得信道
            Channel channel = conn.createChannel();
            //声明交换器
            String exchangeName = "hello-exchange";

            channel.exchangeDeclare(exchangeName, "direct", true);
            String routingKey = "hola";

            channel.queueDeclare(exchangeName,true,false,false,null);
            //发布消息
            byte[] messageBodyBytes = "你好!".getBytes();
            channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);

            channel.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
