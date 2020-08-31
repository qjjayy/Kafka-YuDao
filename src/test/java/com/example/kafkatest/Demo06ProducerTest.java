package com.example.kafkatest;

import com.example.kafkatest.producer.Demo06Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description: 并发消费，Kafka 每个线程都会创建一个 Consumer，然后以BIO的方式进行消费，多个Consumer 瓜分 partitions
 *                        RocketMQ 只会创建一个 Consumer，然后以NIO的方式丢到 Consumer 的线程池中执行消费，从而实现并发消费
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Slf4j
public class Demo06ProducerTest extends KafkaTestApplicationTests {

    @Autowired
    private Demo06Producer producer;

    @Test
    public void testSyncSend() throws InterruptedException, ExecutionException {
        for (int i = 0; i < 10; i++) {
            int id = (int) (System.currentTimeMillis() / 1000);
            SendResult<Object, Object> result = producer.syncSend(id);
            log.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);
            new CountDownLatch(1).await(10, TimeUnit.SECONDS);
        }
    }

}
