package com.example.kafkatest.consumer;

import com.example.kafkatest.message.Demo01Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @description:  不同消费组
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Slf4j
@Component
public class Demo01AConsumer {

    @KafkaListener(topics = Demo01Message.TOPIC, groupId = "demo01-A-consumer-group-" + Demo01Message.TOPIC)
    public void onMessage(ConsumerRecord<Integer, String> record) {
        log.info("[onMessage][线程编号:{} 消息内容: {}]", Thread.currentThread().getId(), record);
    }
}
