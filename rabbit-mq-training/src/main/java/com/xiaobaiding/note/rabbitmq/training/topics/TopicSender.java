package com.xiaobaiding.note.rabbitmq.training.topics;

import com.rabbitmq.client.Channel;
import com.xiaobaiding.note.rabbitmq.common.RabbitMQFactory;
import com.xiaobaiding.note.rabbitmq.constants.RabbitMQConstants;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * * ：可以替代一个词
 *
 * #：可以替代0或者更多的词
 */
public class TopicSender {

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQFactory factory = new RabbitMQFactory();
        Channel channel = factory.getChannel();
        //	topic 类似Direct，可以模糊匹配和多条件匹配
        channel.exchangeDeclare(RabbitMQConstants.EXCHANGE_TOPIC_NAME,"topic");
        String[] routingKeys = new String[]{
                "quick.orange.rabbit",
                "lazy.orange.elephant",
                "quick.orange.fox",
                "lazy.brown.fox",
                "quick.brown.fox",
                "quick.orange.male.rabbit",
                "com.xiaobaiding.note",
                "com.xbd-note",
                "lazy.orange.male.rabbit"
        };
        for (String routingKey : routingKeys) {
            String msg = "come to "+ routingKey+" routingKey message ";
            channel.basicPublish(RabbitMQConstants.EXCHANGE_TOPIC_NAME,routingKey,null,msg.getBytes("utf-8"));
            System.out.println("TopicSender has been send " + routingKey + " message : " + msg);
        }
        channel.close();
        channel.getConnection().close();
    }
}
