package com.xiaobaiding.note.rabbitmq.training.helloRabbitMQ;

import com.rabbitmq.client.*;
import com.xiaobaiding.note.rabbitmq.common.RabbitMQFactory;
import com.xiaobaiding.note.rabbitmq.constants.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConsumer {

    public RabbitMQConsumer() {
        RabbitMQFactory rf = new RabbitMQFactory();
        ConnectionFactory factory = rf.getConnectionFactory();
        try {
            //创建新的连接
            Connection connection = factory.newConnection();

            //创建新的通道
            Channel channel = connection.createChannel();
            //声明所关注的队列
            channel.queueDeclare(RabbitMQConstants.CONSUMER_QUEUE_NAME, false, false, false, null);
            System.out.println("Consume waiting receive message");

            //通知服务，我们所需要的的通道信息，有信息就回调:handleDelivery
            Consumer consumer = new DefaultConsumer(channel) {
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    //收到的消息
                    String message = new String(body);
                    //开启保存数据的线程操作
                    System.out.println("we got the message :" + message);
                }
            };
            //回复队列，确认收到信息-rabbitMQ消息确认机制
            channel.basicConsume(RabbitMQConstants.CONSUMER_QUEUE_NAME, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
