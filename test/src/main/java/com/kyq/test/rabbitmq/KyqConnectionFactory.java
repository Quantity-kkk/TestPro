package com.kyq.test.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;

/**
 * Description:
 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-04-04 13:45
 */
public class KyqConnectionFactory extends ConnectionFactory{
    public KyqConnectionFactory(){
        this.setHost(RabbitMQAttribute.RABBIT_MQ_HOST);
        this.setUsername(RabbitMQAttribute.RABBIT_MQ_USER_NAME);
        this.setPassword(RabbitMQAttribute.RABBIT_MQ_PASS_WORD);
        this.setHandshakeTimeout(RabbitMQAttribute.RABBIT_MQ_HAND_SHAKE_TIME_OUT);
    }
}
