package com.xiaobaiding.note.rabbitmq.training.tasks;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.xiaobaiding.note.rabbitmq.common.RabbitMQFactory;
import com.xiaobaiding.note.rabbitmq.constants.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQFactory factory = new RabbitMQFactory();
        Channel channel = factory.getChannel();
        channel.queueDeclare(RabbitMQConstants.TASK_QUEUE_NAME, true, false, false, null);
        for (int i = 0; i < 100; i++) {
            String msg = "Hello RabbitMQ _ " + i;
            channel.basicPublish("", RabbitMQConstants.TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes("utf-8"));
            System.out.println(Thread.currentThread().getName() + "-->NewTask send" + msg);
        }
        channel.close();
        channel.getConnection().close();
    }
}
