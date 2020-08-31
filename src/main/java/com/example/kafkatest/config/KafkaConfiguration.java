package com.example.kafkatest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.*;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

/**
 * @description:
 * @author: QiuJJ
 * @create: 2020-08-31
 **/
@Configuration
public class KafkaConfiguration {

    @Bean
    @Primary
    public ErrorHandler kafkaErrorHandler(KafkaTemplate<?, ?> template) {
        // 投送到死信队列
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(template);
        FixedBackOff backOff = new FixedBackOff(10 * 1000, 3);
//        ExponentialBackOff backOff = new ExponentialBackOff();  // 指数避退，默认初始间隔为2000ms，避退因子为1.5，最长间隔为30000ms
        return new SeekToCurrentErrorHandler(recoverer, backOff);
        // 会调用 Kafka Consumer 的 seek(TopicPartition partition, long offset) 方法，将 Consumer 对于该消息对应的 TopicPartition 分区的本地进度设置成该消息的位置。
        // 这样，Consumer 在下次从 Kafka Broker 拉取消息的时候，又能重新拉取到这条消费失败的消息，并且是第一条。
        // Spring-Kafka 使用 FailedRecordTracker 对每个 Topic 的每个 TopicPartition 消费失败次数进行计数，这样相当于对该 TopicPartition 的第一条消费失败的消息的消费失败次数进行计数。
        // 另外，在 FailedRecordTracker 中，会调用 BackOff 来进行计算，该消息的下一次重新消费的时间，通过 Thread.sleep 方法，实现重新消费的时间间隔。
        // FailedRecordTracker 提供的计数是客户端级别的，重启 JVM 应用后，计数是会丢失的。所以，如果想要计数进行持久化，需要自己重新实现下 FailedRecordTracker 类，通过 ZooKeeper 存储计数。
    }

//    @Bean
//    @Primary
//    public BatchErrorHandler kafkaBatchErrorHandler() {
//        // 消息的批量消费失败的消费重试处理，暂时不支持死信队列
//        BackOff backOff = new FixedBackOff(10 * 1000, 3);
//        SeekToCurrentBatchErrorHandler batchErrorHandler = new SeekToCurrentBatchErrorHandler();
//        batchErrorHandler.setBackOff(backOff);
//        return batchErrorHandler;
//    }
}
