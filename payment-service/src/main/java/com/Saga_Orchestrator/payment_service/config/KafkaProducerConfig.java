package com.Saga_Orchestrator.payment_service.config;


import com.Saga_Orchestrator.common.services.event.UserBalanceUpdateEvent;
import com.Saga_Orchestrator.payment_service.event.PaymentCompletedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;


import com.Saga_Orchestrator.common.services.event.UserBalanceUpdateEvent;
import com.Saga_Orchestrator.payment_service.event.PaymentCompletedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;


import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    // ProducerFactory for UserBalanceUpdateEvent
    @Bean
    public ProducerFactory<String, UserBalanceUpdateEvent> userBalanceProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean(name = "userBalanceKafkaTemplate")
    public KafkaTemplate<String, UserBalanceUpdateEvent> userBalanceKafkaTemplate() {
        return new KafkaTemplate<>(userBalanceProducerFactory());
    }

    // ProducerFactory for PaymentCompletedEvent
    @Bean
    public ProducerFactory<String, PaymentCompletedEvent> paymentCompletedProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean(name = "paymentCompletedKafkaTemplate")
    public KafkaTemplate<String, PaymentCompletedEvent> paymentCompletedKafkaTemplate() {
        return new KafkaTemplate<>(paymentCompletedProducerFactory());
    }
}




