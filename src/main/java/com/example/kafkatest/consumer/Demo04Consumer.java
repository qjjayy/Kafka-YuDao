package com.example.kafkatest.consumer;

import com.example.kafkatest.message.Demo04Message;
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
public class Demo04Consumer {

    @KafkaListener(topics = Demo04Message.TOPIC, groupId = "demo04-consumer-group-" + Demo04Message.TOPIC)
    public void onMessage(Demo04Message message) {
        log.info("[onMessage][线程编号:{} 消息内容: {}]", Thread.currentThread().getId(), message);
        // 故意抛出一个 RuntimeException 异常，模拟消费失败
        throw new RuntimeException("故意的异常");
    }
}
