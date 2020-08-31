package com.example.kafkatest.consumer;

import com.example.kafkatest.message.Demo05Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Slf4j
@Component
public class Demo05Consumer {

    // groupId 使用 UUID 确保每个 ConsumerGroup 都只有一个 Consumer
    @KafkaListener(topics = Demo05Message.TOPIC, groupId = "demo05-consumer-group-" + Demo05Message.TOPIC + "-" + "#{T(java.util.UUID).randomUUID()}")
    public void onMessage(Demo05Message message) {
        log.info("[onMessage][线程编号:{} 消息内容: {}]", Thread.currentThread().getId(), message);
    }
}
