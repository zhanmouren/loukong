package com.koron.inwlms.util.kafka;

/**
 * kafka参数配置
 * @author 刘刚
 *
 */
public interface KafkaProperties {
	
	final static String zkConnect = "10.13.11.1:9092";
    final static String groupId = "group1";
    final static String topic = "test2";
    final static String kafkaServerURL = "10.13.11.1";
    final static int kafkaServerPort = 9092;
    final static int kafkaProducerBufferSize = 64 * 1024;
    final static int connectionTimeOut = 20000;
    final static int reconnectInterval = 10000;
    final static String topic2 = "test1";
    final static String topic3 = "test";
    final static String clientId = "SimpleConsumerDemoClient";
	
}
