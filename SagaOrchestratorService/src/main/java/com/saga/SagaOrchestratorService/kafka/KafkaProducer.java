package com.saga.SagaOrchestratorService.kafka;


import com.saga.SagaOrchestratorService.event.SagaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Component

public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishToTopic(String topicName, Object event) {
        kafkaTemplate.send(topicName, event);
        log.info("Published to Kafka topic {}: {}", topicName, event);
    }

}
