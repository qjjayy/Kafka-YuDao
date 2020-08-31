package com.example.kafkatest.producer;

import com.example.kafkatest.message.Demo01Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Component
public class Demo01Producer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public SendResult syncSend(Integer id) throws ExecutionException, InterruptedException {
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // 同步发送消息
        return kafkaTemplate.send(Demo01Message.TOPIC, message).get();
    }

    public ListenableFuture<SendResult<Object, Object>> asyncSend(Integer id) {
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // 异步发送消息
        return kafkaTemplate.send(Demo01Message.TOPIC, message);
    }
}
