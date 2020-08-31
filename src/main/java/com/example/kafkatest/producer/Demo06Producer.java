package com.example.kafkatest.producer;

import com.example.kafkatest.message.Demo06Message;
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
public class Demo06Producer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public SendResult<Object, Object> syncSend(Integer id) throws ExecutionException, InterruptedException {
        Demo06Message message = new Demo06Message();
        message.setId(id);
        // 同步发送消息
        return kafkaTemplate.send(Demo06Message.TOPIC, message).get();
    }
}
