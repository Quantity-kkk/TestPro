package com.kyq.test.rabbitmq.rpc;

import com.kyq.test.rabbitmq.KyqConnectionFactory;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-04-04 15:03
 */
public class RPCServer {
    private static final String RPC_QUEUE_NAME = "rpc_queue";

    private static int fib(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] argv) {
        Connection connection = null;
        try {
            ConnectionFactory factory = new KyqConnectionFactory();

            connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

            channel.basicQos(1);
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body) throws IOException{
                    String mes=new String(body,"UTF-8");
                    System.out.println(envelope.getRoutingKey()+":Received :'"+mes+"' done");
                    //接收到消息后，向发送方回写消息：指定发送方所在队列(properties.getReplyTo())、correlationId
                    channel.basicPublish("", properties.getReplyTo() ,
                            new AMQP.BasicProperties.Builder().correlationId(properties.getCorrelationId()).build(),
                            ("response:"+fib(Integer.valueOf(mes))).getBytes());
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
            System.out.println("RPCServer [x] Awaiting RPC requests");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
