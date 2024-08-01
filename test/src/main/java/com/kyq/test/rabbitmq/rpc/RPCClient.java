package com.kyq.test.rabbitmq.rpc;

import com.kyq.test.rabbitmq.KyqConnectionFactory;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-04-04 15:06
 */
public class RPCClient {
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String replyQueueName;
    private Consumer consumer;
    final String corrId = UUID.randomUUID().toString();
    public RPCClient() throws Exception {
        ConnectionFactory factory = new KyqConnectionFactory();
        connection = factory.newConnection();
        channel = connection.createChannel();
        replyQueueName = channel.queueDeclare().getQueue();

        consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       BasicProperties properties,
                                       byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(corrId)) {
                    String response = new String(body, "UTF-8");
                    System.out.println("RPCClient [.] Got '" + response + "'");
                    //获得返回结果后关闭
                    connection.close();
                }
            }
        };
        channel.basicConsume(replyQueueName, true, consumer);
    }

    public void call(String message) throws Exception {
        BasicProperties props = new BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();
        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));
    }
    public static void main(String[] argv) {
        RPCClient fibonacciRpc = null;
        try {
            fibonacciRpc = new RPCClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("RPCClient [x] Requesting fib(30)");
        try {
            fibonacciRpc.call("30");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
