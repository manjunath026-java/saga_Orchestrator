package com.Saga_Orchestrator.user_service.kafka;

import com.Saga_Orchestrator.common.services.event.UserBalanceUpdateEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {





        @Bean
        public ConsumerFactory<String, UserBalanceUpdateEvent> consumerFactory() {
            Map<String, Object> config = new HashMap<>();
            config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            config.put(ConsumerConfig.GROUP_ID_CONFIG, "user-group");
            config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

            return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                    new JsonDeserializer<>(UserBalanceUpdateEvent.class));
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, UserBalanceUpdateEvent> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, UserBalanceUpdateEvent> factory =
                    new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
        }
    }



