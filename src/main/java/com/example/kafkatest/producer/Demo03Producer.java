package com.example.kafkatest.producer;

import com.example.kafkatest.message.Demo03Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

/**
 * @description:
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Component
public class Demo03Producer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public ListenableFuture<SendResult<Object, Object>> asyncSend(Integer id) {
        Demo03Message message = new Demo03Message();
        message.setId(id);
        // 异步发送消息
        return kafkaTemplate.send(Demo03Message.TOPIC, message);
    }
}
