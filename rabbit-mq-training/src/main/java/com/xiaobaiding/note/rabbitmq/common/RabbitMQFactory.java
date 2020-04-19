package com.xiaobaiding.note.rabbitmq.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQFactory {
    private String host = "192.168.0.87";
    private String username = "admin";
    private String password = "admin";
    private Integer port = 5672;
    private ConnectionFactory connectionFactory;

    private ConnectionFactory createFactory() {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置工厂属性
        // 连接地址DEFAULT_HOST = "localhost"
        factory.setHost(host);
        //设置访问用户，default=guest，rabbitmq在3.3以后禁止使用guest操作
        factory.setUsername(username);
        //设置访问用户密码，default=guest，rabbitmq在3.3以后禁止使用guest操作
        factory.setPassword(password);
        factory.setPort(port);
        return factory;
    }

    public RabbitMQFactory() {
        ConnectionFactory factory = createFactory();
        connectionFactory = factory;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public Channel getChannel() throws IOException, TimeoutException {

        //创建新的连接
        Connection connection = connectionFactory.newConnection();

        //创建新的通道
        Channel channel = connection.createChannel();
        return channel;
    }

}
