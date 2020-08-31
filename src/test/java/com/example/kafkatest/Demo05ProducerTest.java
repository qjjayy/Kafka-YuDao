package com.example.kafkatest;

import com.example.kafkatest.producer.Demo05Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description: 广播消费，Kafka 不直接提供内置的广播消费功能，通过每个 Consumer 独有一个 Consumer Group，从而保证能接收到全量的消息
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Slf4j
public class Demo05ProducerTest extends KafkaTestApplicationTests {

    @Autowired
    private Demo05Producer producer;

    @Test
    public void test() throws InterruptedException {
        // 阻塞等待，保证消费
        new CountDownLatch(1).await(120, TimeUnit.SECONDS);
    }

    @Test
    public void testSyncSend() throws InterruptedException, ExecutionException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult<Object, Object> result = producer.syncSend(id);
        log.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);
        new CountDownLatch(1).await(10, TimeUnit.SECONDS);
    }

}
