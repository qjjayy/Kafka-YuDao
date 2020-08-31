package com.example.kafkatest.producer;

import com.example.kafkatest.message.Demo07Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaOperations;
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
public class Demo07Producer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public String syncSendInTransaction(Integer id, Runnable runner) {
        return kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback<Object, Object, String>() {
            @Override
            public String doInOperations(KafkaOperations<Object, Object> kafkaOperations) {
                Demo07Message message = new Demo07Message();
                message.setId(id);
                try {
                    SendResult<Object, Object> sendResult = kafkaOperations.send(Demo07Message.TOPIC, message).get();
                    log.info("[doInOperations][发送编号：[{}] 发送结果：[{}]]", id, sendResult);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                runner.run(); // 本地业务逻辑
                return "success";
            }
        });
    }
}
