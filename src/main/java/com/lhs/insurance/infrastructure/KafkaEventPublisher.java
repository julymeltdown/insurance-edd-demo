package com.lhs.insurance.infrastructure;

import com.lhs.insurance.event.InsuranceApplicationAcceptedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public KafkaEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(InsuranceApplicationAcceptedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
