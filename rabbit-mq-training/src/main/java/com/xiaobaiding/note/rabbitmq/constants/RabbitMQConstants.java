package com.xiaobaiding.note.rabbitmq.constants;

public class RabbitMQConstants {

    //消息队列
    public final static String QUEUE_NAME = "java_test_1";
    public final static String CONSUMER_QUEUE_NAME = "CONSUMER_QUEUE_NAME";
    public final static String TASK_QUEUE_NAME = "TASK_QUEUE_NAME";
    public final static String EXCHANGE_NAME = "LOGS";
    public final static String EXCHANGE_ROUTING_NAME = "ROUTING_LOGS";
    public final static String EXCHANGE_TOPIC_NAME = "TOPIC_LOGS";

    public static final String[] ROUTING_KEYS = new String[]{
            "info","warning","error"
    };

}
