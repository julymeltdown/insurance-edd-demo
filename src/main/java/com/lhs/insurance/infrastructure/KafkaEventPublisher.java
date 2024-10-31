package com.lhs.insurance.infrastructure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lhs.insurance.event.InsuranceApplicationAcceptedEvent;
import com.lhs.insurance.exception.KafkaPublishException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(InsuranceApplicationAcceptedEvent event) {
        String topicName = "insurance-application-approved";
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topicName, message);
        } catch (JsonProcessingException e) {
            throw new KafkaPublishException("Failed to publish event to Kafka.", e);
        }
    }
}