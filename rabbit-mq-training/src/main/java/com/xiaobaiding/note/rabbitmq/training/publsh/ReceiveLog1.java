package com.xiaobaiding.note.rabbitmq.training.publsh;

import com.rabbitmq.client.*;
import com.xiaobaiding.note.rabbitmq.common.RabbitMQFactory;
import com.xiaobaiding.note.rabbitmq.constants.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLog1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQFactory factory = new RabbitMQFactory();
        Channel channel = factory.getChannel();
        //去获取全部消息
        channel.exchangeDeclare(RabbitMQConstants.EXCHANGE_NAME, "fanout");
        String queue = channel.queueDeclare().getQueue();
        //绑定队列和转换器
        channel.queueBind(queue, RabbitMQConstants.EXCHANGE_NAME, "");
        System.out.println(Thread.currentThread().getId() + " waiting message");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println(Thread.currentThread().getId() + " received Msg:" + msg);
            }
        };
        channel.basicConsume(queue, true, consumer);
    }
}
