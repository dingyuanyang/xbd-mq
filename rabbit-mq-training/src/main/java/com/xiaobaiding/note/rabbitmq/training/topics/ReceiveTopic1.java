package com.xiaobaiding.note.rabbitmq.training.topics;

import com.rabbitmq.client.*;
import com.xiaobaiding.note.rabbitmq.common.RabbitMQFactory;
import com.xiaobaiding.note.rabbitmq.constants.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveTopic1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQFactory factory = new RabbitMQFactory();
        Channel channel = factory.getChannel();
        channel.exchangeDeclare(RabbitMQConstants.EXCHANGE_TOPIC_NAME, "topic");
        String queue = channel.queueDeclare().getQueue();
        String[] routingKeys = new String[]{
                "*.orange.*","com."
        };
        for (String routingKey : routingKeys) {
            channel.queueBind(queue, RabbitMQConstants.EXCHANGE_TOPIC_NAME, routingKey);
            System.out.println("ReceiveTopic1 exchange:" + RabbitMQConstants.EXCHANGE_TOPIC_NAME + ", queue:" + queue + ", BindRoutingKey:" + routingKey);
        }
        System.out.println("ReceiveTopic1 waiting for msg");
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException
            {
                String msg = new String(body,"utf-8");
                System.out.println("ReceiveTopic1 get message -->" + envelope.getRoutingKey() + ":" + msg);
            }
        };
        channel.basicConsume(queue,true,consumer);
    }
}
