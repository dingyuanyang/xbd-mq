package com.xiaobaiding.note.rabbitmq.training.publsh;

import com.rabbitmq.client.Channel;
import com.xiaobaiding.note.rabbitmq.common.RabbitMQFactory;
import com.xiaobaiding.note.rabbitmq.constants.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 广播发布消息
 */
public class EmitLog {

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQFactory factory = new RabbitMQFactory();
        Channel channel = factory.getChannel();
        //fanout 广播：将所有发送到Exchange的消息路由到与之绑定的Queue
        channel.exchangeDeclare(RabbitMQConstants.EXCHANGE_NAME, "fanout");

        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ _ " + i;
            //不指定消息队列，往交换机发送
            channel.basicPublish(RabbitMQConstants.EXCHANGE_NAME, "", null, msg.getBytes("utf-8"));
            System.out.println("EmitLog sent:" + msg);
        }
        channel.close();
        channel.getConnection().close();
    }
}
