package com.example.kafkatest.producer;

import com.example.kafkatest.message.Demo05Message;
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
@Component
public class Demo05Producer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public SendResult<Object, Object> syncSend(Integer id) throws ExecutionException, InterruptedException {
        Demo05Message message = new Demo05Message();
        message.setId(id);
        // 同步发送消息
        return kafkaTemplate.send(Demo05Message.TOPIC, message).get();
    }
}
