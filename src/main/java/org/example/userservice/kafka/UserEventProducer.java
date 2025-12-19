package org.example.userservice.kafka;

import org.example.userservice.dto.UserEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventProducer {
    private static final Logger log = LoggerFactory.getLogger(UserEventProducer.class);
    private final KafkaTemplate<String, UserEventDto> kafkaTemplate;
    private static final String TOPIC = "user-events";

    public UserEventProducer(KafkaTemplate<String, UserEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(UserEventDto event) {
        kafkaTemplate.send(TOPIC, event);
        log.info("Sent event: {}", event);
    }
}
