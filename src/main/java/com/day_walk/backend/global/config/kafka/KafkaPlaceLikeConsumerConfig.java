package com.day_walk.backend.global.config.kafka;

import com.day_walk.backend.domain.place_like.data.out.PlaceLikeEvent;
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
public class KafkaPlaceLikeConsumerConfig {

    @Bean
    public ConsumerFactory<String, PlaceLikeEvent> placeLikeConsumerFactory() {
        JsonDeserializer<PlaceLikeEvent> deserializer = new JsonDeserializer<>(PlaceLikeEvent.class);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PlaceLikeEvent> placeLikeKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PlaceLikeEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(placeLikeConsumerFactory());
        return factory;
    }
}
