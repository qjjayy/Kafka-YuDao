package com.example.kafkatest.producer;

import com.example.kafkatest.message.Demo08Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Slf4j
@Component
public class Demo08Producer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public SendResult<Object, Object> syncSend(Integer id) throws ExecutionException, InterruptedException {
        Demo08Message message = new Demo08Message();
        message.setId(id);
        // 同步发送消息
        return kafkaTemplate.send(Demo08Message.TOPIC, message).get();
    }

}
