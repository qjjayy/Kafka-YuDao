package com.example.kafkatest;

import com.example.kafkatest.producer.Demo04Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description:  消费重试，在 Kafka 中消费重试和死信队列不是自带的，而是由 Spring-Kafka 实现的
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Slf4j
public class Demo04ProducerTest extends KafkaTestApplicationTests {

    @Autowired
    private Demo04Producer producer;

    @Test
    public void testSyncSend() throws InterruptedException, ExecutionException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult<Object, Object> result = producer.syncSend(id);
        log.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);
        new CountDownLatch(1).await(60, TimeUnit.SECONDS);
    }

}
