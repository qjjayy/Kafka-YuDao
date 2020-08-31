package com.example.kafkatest.consumer;

import com.example.kafkatest.message.Demo06Message;
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
public class Demo06Consumer {

    @KafkaListener(topics = Demo06Message.TOPIC, groupId = "demo06-consumer-group-" + Demo06Message.TOPIC, concurrency = "2")
    public void onMessage(Demo06Message message) {
        log.info("[onMessage][线程编号:{} 消息内容: {}]", Thread.currentThread().getId(), message);
    }
}
