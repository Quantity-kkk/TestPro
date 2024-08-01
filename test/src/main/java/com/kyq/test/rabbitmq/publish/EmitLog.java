package com.kyq.test.rabbitmq.publish;

import com.kyq.test.rabbitmq.RabbitMQAttribute;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-04-02 14:41
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static synchronized ConnectionFactory getConnectionFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQAttribute.RABBIT_MQ_HOST);
        factory.setUsername(RabbitMQAttribute.RABBIT_MQ_USER_NAME);
        factory.setPassword(RabbitMQAttribute.RABBIT_MQ_PASS_WORD);
        factory.setHandshakeTimeout(RabbitMQAttribute.RABBIT_MQ_HAND_SHAKE_TIME_OUT);
        return factory;
    }
    public static void main(String args[]){
        ConnectionFactory factory = getConnectionFactory();
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            //分发消息
            for(int i = 0 ; i < 5; i++){
                String message = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format( new Date())+": Hello World! " + i;
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }
}
