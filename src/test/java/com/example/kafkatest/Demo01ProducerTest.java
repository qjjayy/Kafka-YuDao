package com.example.kafkatest;

import com.example.kafkatest.producer.Demo01Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description: 基础的同步发送和异步发送
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Slf4j
public class Demo01ProducerTest extends KafkaTestApplicationTests {

    @Autowired
    private Demo01Producer producer;

    @Test
    public void testSyncSend() throws ExecutionException, InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult sendResult = producer.syncSend(id);
        log.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, sendResult);

        new CountDownLatch(1).await(5, TimeUnit.SECONDS);
    }

    @Test
    public void testAsyncSend() throws InterruptedException {
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

        new CountDownLatch(1).await(5, TimeUnit.SECONDS);
    }

}
