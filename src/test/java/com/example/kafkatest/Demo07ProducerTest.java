package com.example.kafkatest;

import com.example.kafkatest.producer.Demo07Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description: 事务消息，没有回查机制，不具备完整的事务消息支持
 *               Spring-Kafka 提供了 Spring Transaction 的集成，所以也可以只使用 @Transactional 注解来声明事务即可，不需要通过 kafkaTemplate.executeInTransaction 方法
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Slf4j
public class Demo07ProducerTest extends KafkaTestApplicationTests {

    @Autowired
    private Demo07Producer producer;

    @Test
    public void testSyncSendInTransaction() throws InterruptedException, ExecutionException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSendInTransaction(id, new Runnable() {
            @Override
            public void run() {
                log.info("[run][我要开始睡觉了]");
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("[run][我睡醒了]");
            }
        });
        new CountDownLatch(1).await(10, TimeUnit.SECONDS);
    }

}
