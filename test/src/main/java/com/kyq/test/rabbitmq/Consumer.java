package com.kyq.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-03-27 16:13
 */
public class Consumer {
    public static void main(String args[]) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername("kyq1024");
            factory.setPassword("kyq1024");
            factory.setHost("192.168.177.130");
            factory.setHandshakeTimeout(15000);//设置握手超时时间,虚拟机响应经常超时。
            //建立到代理服务器到连接
            Connection conn = factory.newConnection();
            //获得信道
            final Channel channel = conn.createChannel();
            //声明交换器
            String exchangeName = "hello-exchange";
            channel.exchangeDeclare(exchangeName, "direct", true);
            //声明队列
            String queueName = channel.queueDeclare().getQueue();
//            channel.queueDeclare("hello_queue",true,true,true,null).getQueue();

            String routingKey = "hola";
            //绑定队列，通过键 hola 将队列和交换器绑定起来
            channel.queueBind(queueName, exchangeName, routingKey);
            //消费消息
            boolean autoAck = false;
            String consumerTag = "";
            channel.basicConsume(queueName, autoAck, consumerTag, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    System.out.println("消费的路由键：" + routingKey);
                    System.out.println("消费的内容类型：" + contentType);
                    long deliveryTag = envelope.getDeliveryTag();
                    //确认消息
                    channel.basicAck(deliveryTag, false);
                    System.out.println("消费的消息体内容：");
                    String bodyStr = new String(body, "UTF-8");
                    System.out.println(bodyStr);

                }
            });
        } catch (IOException e){
            e.printStackTrace();
        }
        catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
