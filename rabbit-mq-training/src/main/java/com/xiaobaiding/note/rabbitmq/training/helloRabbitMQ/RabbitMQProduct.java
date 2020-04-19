package com.xiaobaiding.note.rabbitmq.training.helloRabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xiaobaiding.note.rabbitmq.common.RabbitMQFactory;
import com.xiaobaiding.note.rabbitmq.constants.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQProduct {

    private Channel channel;

    public RabbitMQProduct() {

        try {
            RabbitMQFactory rf = new RabbitMQFactory();
            ConnectionFactory factory = rf.getConnectionFactory();
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(RabbitMQConstants.CONSUMER_QUEUE_NAME, false, false, false, null);
            System.out.println("product waiting to publish message");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    public void sendMsg(String msg) throws IOException {
        channel.basicPublish("", RabbitMQConstants.CONSUMER_QUEUE_NAME, null, msg.getBytes("utf-8"));
        System.out.println("product send message:" + msg);
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        channel.getConnection().close();
    }
}
