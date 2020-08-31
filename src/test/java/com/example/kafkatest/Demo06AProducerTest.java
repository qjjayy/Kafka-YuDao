package com.example.kafkatest;

import com.example.kafkatest.producer.Demo06AProducer;
import com.example.kafkatest.producer.Demo06Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description: 顺序消费，由于 Partition 下的消息能够顺序消费，因此只需要将消息发送到同一 Partition 中即可
 *               Producer 会根据消息中的 key 的哈希值取模来获取其在 Topic 下对应的 Partition
 *               Kafka 中同一 Partition 只会被一个固定的 Consumer线程消费，因此并发场景下也能顺序消费
 *               而 RocketMQ 中同一 ConsumeQueue 会被不同的线程消费，因此并发场景下不能实现顺序消费
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Slf4j
public class Demo06AProducerTest extends KafkaTestApplicationTests {

    @Autowired
    private Demo06AProducer producer;

    @Test
    public void testSyncSend() throws InterruptedException, ExecutionException {
        for (int i = 0; i < 10; i++) {
            int id = (int) (System.currentTimeMillis() / 1000);
            SendResult<Object, Object> result = producer.syncSend(id);
            log.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);
            Thread.sleep(1000);
        }
        new CountDownLatch(1).await(10, TimeUnit.SECONDS);
    }

}
