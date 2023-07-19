package com.lyr.analysis.consumer;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    // 填写问卷,kafka发送到qs_analysis模块进行统计
    @KafkaListener(topics = {"qs-analysis-topic"},groupId = "qs-analysis-group")
    public void analysisQs(ConsumerRecord<?,?> record, Acknowledgment ack,
                             @Header(KafkaHeaders.RECEIVED_TOPIC)String topic){

        System.out.println("消费消息："+record.topic()+"---"+record.partition()+"---"+ record.value());
        // 手动确认已经成功处理完消息，让 Kafka 可以在下一次调用 poll() 方法时更新偏移量。
        ack.acknowledge();
    }
}
