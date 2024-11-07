package com.lhs.insurance.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhs.insurance.domain.entity.InsuranceAgent;
import com.lhs.insurance.domain.entity.InsuranceOffer;
import com.lhs.insurance.infrastructure.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class InsuranceApplicationAcceptedEventListener {

    private final KafkaEventPublisher kafkaEventPublisher;

    @TransactionalEventListener
    public void handleInsuranceApplicationAccepted(InsuranceApplicationAcceptedEvent event) {
        InsuranceOffer insuranceOffer = event.getInsuranceOffer();
        InsuranceAgent agent = event.getInsuranceAgent();
        long commission = event.getCommission();

        // 360 Health 등급 업데이트 메시지 발행
        One360HealthUpdate(insuranceOffer);

        // 설계사 보험금 지급 메시지 발행
        insuranceCommissionPayment(agent, commission);
    }

    private void insuranceCommissionPayment(InsuranceAgent agent, long commission) {
        InsuranceCommissionPaymentMessage insuranceCommissionPaymentMessage = new InsuranceCommissionPaymentMessage(agent.getId(), commission);

        try {
            String message = new ObjectMapper().writeValueAsString(insuranceCommissionPaymentMessage);
            kafkaEventPublisher.publish("insurance-commission-payment", message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 실패", e);
        }
    }

    private void One360HealthUpdate(InsuranceOffer insuranceOffer) {
        One360HealthUpdateMessage healthInsuranceUpdateMessage = new One360HealthUpdateMessage(insuranceOffer.getId(), insuranceOffer.getMainContractPremium(), insuranceOffer.getInsuredPerson().getAge());
        try {
            String message = new ObjectMapper().writeValueAsString(healthInsuranceUpdateMessage);
            kafkaEventPublisher.publish("health-insurance-update", message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 실패", e);
        }
    }
}