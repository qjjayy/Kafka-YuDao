package com.example.kafkatest;

import com.example.kafkatest.producer.Demo08Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description: 提交消费进度
 * 原生 Kafka Consumer 消费端，有两种消费进度提交的提交机制：
 * 【默认】自动提交，通过配置 enable.auto.commit=true ，每过 auto.commit.interval.ms 时间间隔，都会自动提交消费消费进度。而提交的时机，是在 Consumer 的 #poll(...) 方法的逻辑里完成，在每次从 Kafka Broker 拉取消息时，会检查是否到达自动提交的时间间隔，如果是，那么就会提交上一次轮询拉取的位置。
 * 手动提交，通过配置 enable.auto.commit=false ，后续通过 Consumer 的 #commitSync(...) 或 #commitAsync(...) 方法，同步或异步提交消费进度。
 *
 * Spring-Kafka Consumer 消费端，提供了更丰富的消费者进度的提交机制，更加灵活。当然，也是分成自动提交和手动提交两个大类。在 AckMode 枚举类中，可以看到每一种具体的方式
 *
 * 使用原生 Kafka 的方式，通过配置 spring.kafka.consumer.enable-auto-commit=true 。然后，通过 spring.kafka.consumer.auto-commit-interval 设置自动提交的频率。
 * 使用 Spring-Kafka 的方式，通过配置 spring.kafka.consumer.enable-auto-commit=false 。然后通过 spring.kafka.listener.ack-mode 设置具体模式。另外，还有 spring.kafka.listener.ack-time 和 spring.kafka.listener.ack-count 可以设置自动提交的时间间隔和消息条数。
 *
 * 默认什么都不配置的情况下，使用 Spring-Kafka 的 BATCH 模式：每一次消息被消费完成后，在下次拉取消息之前，自动提交。
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
// ContainerProperties#AckMode.java

// public enum AckMode {

// ========== 自动提交 ==========

    /**
     * Commit after each record is processed by the listener.
     */
    // RECORD, // 每条消息被消费完成后，自动提交

    /**
     * Commit whatever has already been processed before the next poll.
     */
    // BATCH, // 每一次消息被消费完成后，在下次拉取消息之前，自动提交

    /**
     * Commit pending updates after
     * {@link ContainerProperties#setAckTime(long) ackTime} has elapsed.
     */
    // TIME, // 达到一定时间间隔后，自动提交。
    // 不过要注意，它并不是一到就立马提交，如果此时正在消费某一条消息，需要等这条消息被消费完成，才能提交消费进度。

    /**
     * Commit pending updates after
     * {@link ContainerProperties#setAckCount(int) ackCount} has been
     * exceeded.
     */
    // COUNT, // 消费成功的消息数到达一定数量后，自动提交。
    // 不过要注意，它并不是一到就立马提交，如果此时正在消费某一条消息，需要等这条消息被消费完成，才能提交消费进度。

    /**
     * Commit pending updates after
     * {@link ContainerProperties#setAckCount(int) ackCount} has been
     * exceeded or after {@link ContainerProperties#setAckTime(long)
     * ackTime} has elapsed.
     */
    // COUNT_TIME, // TIME 和 COUNT 的结合体，满足任一都会自动提交。

// ========== 手动提交 ==========

    /**
     * User takes responsibility for acks using an
     * {@link AcknowledgingMessageListener}.
     */
    // MANUAL, // 调用时，先标记提交消费进度。等到当前消息被消费完成，然后在提交消费进度。

    /**
     * User takes responsibility for acks using an
     * {@link AcknowledgingMessageListener}. The consumer
     * immediately processes the commit.
     */
    // MANUAL_IMMEDIATE, // 调用时，立即提交消费进度。

// }
@Slf4j
public class Demo08ProducerTest extends KafkaTestApplicationTests {

    @Autowired
    private Demo08Producer producer;

    @Test
    public void testSyncSend() throws InterruptedException, ExecutionException {
        for (int id = 0; id < 2; id++) {
            SendResult<Object, Object> result = producer.syncSend(id);
            log.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);
        }
        new CountDownLatch(1).await(10, TimeUnit.SECONDS);
    }

}
