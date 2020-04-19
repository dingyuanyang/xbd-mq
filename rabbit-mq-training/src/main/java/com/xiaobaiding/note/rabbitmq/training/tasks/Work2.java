package com.xiaobaiding.note.rabbitmq.training.tasks;

import com.rabbitmq.client.*;
import com.xiaobaiding.note.rabbitmq.common.RabbitMQFactory;
import com.xiaobaiding.note.rabbitmq.constants.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Work2 {

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQFactory factory = new RabbitMQFactory();
        Channel channel = factory.getChannel();
        channel.queueDeclare(RabbitMQConstants.TASK_QUEUE_NAME, true, false, false, null);
        System.out.println("Worker 2 waiting for message");
        //每次从队列中获取的数量
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("Worker 2 received :" + msg);
                try {
//                    throw new Exception();
                    doWork(msg);
                } catch (Exception e) {
                    channel.abort();
                } finally {
                    System.out.println("Worker 2 done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(RabbitMQConstants.TASK_QUEUE_NAME, autoAck, consumer);
    }

    private static void doWork(String task) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
