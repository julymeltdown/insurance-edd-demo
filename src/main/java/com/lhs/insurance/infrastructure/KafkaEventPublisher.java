package com.lhs.insurance.infrastructure;

// KafkaEventPublisher.java
import com.lhs.insurance.application.dto.InsuranceDto;
import com.lhs.insurance.domain.entity.Applicant;
import com.lhs.insurance.domain.entity.InsuredPerson;
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
