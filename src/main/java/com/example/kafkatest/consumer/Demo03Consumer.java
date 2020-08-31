package com.example.kafkatest.consumer;

import com.example.kafkatest.message.Demo03Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Slf4j
@Component
public class Demo03Consumer {

    @KafkaListener(topics = Demo03Message.TOPIC, groupId = "demo03-consumer-group-" + Demo03Message.TOPIC)
    public void onMessage(List<Demo03Message> messages) {
        log.info("[onMessage][线程编号:{} 消息内容: {}]", Thread.currentThread().getId(), messages);
    }
}
