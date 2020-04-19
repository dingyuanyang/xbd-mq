package com.xiaobaiding.note.rabbitmq.training.routing;

import com.rabbitmq.client.*;
import com.xiaobaiding.note.rabbitmq.common.RabbitMQFactory;
import com.xiaobaiding.note.rabbitmq.constants.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoutingReceiveDirect1 {

    private static final String[] routingKeys = new String[]{
            "info", "warning"
    };

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQFactory factory = new RabbitMQFactory();
        Channel channel = factory.getChannel();
        //声明使用队列
        channel.exchangeDeclare(RabbitMQConstants.EXCHANGE_ROUTING_NAME, "direct");
        //生成随机队列
        String queue = channel.queueDeclare().getQueue();

        for (String routingKey : routingKeys) {
            //绑定所有的获取信息通道
            channel.queueBind(queue, RabbitMQConstants.EXCHANGE_ROUTING_NAME, routingKey);
            System.out.println("exchange=" + RabbitMQConstants.EXCHANGE_ROUTING_NAME + ",queue=" + queue + ",routingKey=" + routingKey);
        }
        System.out.println("RoutingReceiveDirect1 waiting for message");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("RoutingReceiveDirect1 get message -->" + envelope.getRoutingKey() + ":" + msg);
            }

        };
        channel.basicConsume(queue, true, consumer);
    }
}
