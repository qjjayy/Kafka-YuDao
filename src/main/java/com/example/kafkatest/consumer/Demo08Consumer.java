package com.example.kafkatest.consumer;

import com.example.kafkatest.message.Demo08Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Slf4j
@Component
public class Demo08Consumer {

    @KafkaListener(topics = Demo08Message.TOPIC, groupId = "demo07-consumer-group-" + Demo08Message.TOPIC)
    public void onMessage(Demo08Message message, Acknowledgment acknowledgment) {
        log.info("[onMessage][线程编号:{} 消息内容: {}]", Thread.currentThread().getId(), message);
        // 提交消费进度
        if (message.getId() % 2 == 1) {
            acknowledgment.acknowledge();
        }
    }
}
