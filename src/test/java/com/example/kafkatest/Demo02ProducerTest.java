package com.example.kafkatest;

import com.example.kafkatest.producer.Demo02Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @description: 批量发送
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Slf4j
public class Demo02ProducerTest extends KafkaTestApplicationTests {

    @Autowired
    private Demo02Producer producer;

    @Test
    public void testAsyncSend() throws InterruptedException {
        log.info("[testAsyncSend][开始执行]");

        for (int i = 0; i < 3; i++) {
            int id = (int) (System.currentTimeMillis() / 1000);
            producer.asyncSend(id).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, throwable);
                }

                @Override
                public void onSuccess(SendResult<Object, Object> objectObjectSendResult) {
                    log.info("[testASyncSend][发送编号：[{}] 发送成功，结果为：[{}]]", id, objectObjectSendResult);
                }
            });

            // 故意每条消息之间，隔离10s
            Thread.sleep(10 * 1000);
        }

        new CountDownLatch(1).await(5, TimeUnit.SECONDS);
    }

}
