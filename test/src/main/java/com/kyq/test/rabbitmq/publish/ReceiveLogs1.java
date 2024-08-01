package com.kyq.test.rabbitmq.publish;

import com.rabbitmq.client.*;

import java.io.UnsupportedEncodingException;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-04-02 14:49
 */
public class ReceiveLogs1 {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = EmitLog.getConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String queueName = channel.queueDeclare().getQueue();
        //绑定关系中使用的路由关键字【routingkey】是否有效取决于交换器的类型。
        // 如果交换器是分发【fanout】类型，就会忽略路由关键字【routingkey】的作用。
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println("ReceiveLogs1 [*] Waiting for messages. To exit press CTRL+C");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body){
                String message = null;
                try {
                    message = new String(body, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("ReceiveLogs1 [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
