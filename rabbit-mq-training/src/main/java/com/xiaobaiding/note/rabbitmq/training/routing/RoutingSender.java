package com.xiaobaiding.note.rabbitmq.training.routing;

import com.rabbitmq.client.Channel;
import com.xiaobaiding.note.rabbitmq.common.RabbitMQFactory;
import com.xiaobaiding.note.rabbitmq.constants.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由分配
 */
public class RoutingSender {

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQFactory factory = new RabbitMQFactory();
        Channel channel = factory.getChannel();
        //将消息发送到BindingKey和Routing Key相匹配的Queue
        channel.exchangeDeclare(RabbitMQConstants.EXCHANGE_ROUTING_NAME, "direct");

        for (String routingKey : RabbitMQConstants.ROUTING_KEYS) {
            String msg = "the message level is : " + routingKey;
            channel.basicPublish(RabbitMQConstants.EXCHANGE_ROUTING_NAME, routingKey, null, msg.getBytes("utf-8"));
            System.out.println("send message " + routingKey + " : " + msg);
        }
        channel.close();
        channel.getConnection().close();
    }
}
