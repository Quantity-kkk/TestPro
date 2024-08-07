package com.kyq.test.rabbitmq.helloword;

import com.rabbitmq.client.*;

import java.io.UnsupportedEncodingException;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-04-02 9:51
 */
public class Consumer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
//      设置RabbitMQ地址
        factory.setHost("192.168.177.130");
        factory.setUsername("kyq1024");
        factory.setPassword("kyq1024");
        factory.setHandshakeTimeout(15000);//设置握手超时时间,虚拟机响应经常超时。
//      创建一个新的连接
        Connection connection = factory.newConnection();
//      创建一个频道
        Channel channel = connection.createChannel();
//      声明要关注的队列 -- 在RabbitMQ中，队列声明是幂等性的（一个幂等操作的特点是其任意多次执行所产生的影响均与一次执行的影响相同），也就是说，如果不存在，就创建，如果存在，不会对已经存在的队列产生任何影响。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("C [*] Waiting for messages. To exit press CTRL+C");
//      DefaultConsumer类实现了Consumer接口，通过传入一个频道，告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = null;
                try {
                    message = new String(body, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("C [x] Received '" + message + "'");
            }
        };
//      自动回复队列应答 -- RabbitMQ中的消息确认机制，后面章节会详细讲解
        String comsumerTag = channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
