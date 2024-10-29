package com.lhs.insurance.application;

import com.lhs.insurance.application.dto.InsuranceDto;
import com.lhs.insurance.domain.entity.*;
import com.lhs.insurance.domain.repository.InsuranceAgentRepository; // 추가
import com.lhs.insurance.domain.repository.InsuranceCommissionRepository;
import com.lhs.insurance.domain.repository.InsuranceOfferRepository;
import com.lhs.insurance.domain.repository.InsuranceProductRepository;
import com.lhs.insurance.event.InsuranceApplicationAcceptedEvent;
import com.lhs.insurance.infrastructure.InsuranceCommissionPolicy;
import com.lhs.insurance.infrastructure.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InsuranceApplicationService {

    private final InsuranceOfferRepository insuranceOfferRepository;
    private final InsuranceCommissionRepository insuranceCommissionRepository;
    private final InsuranceProductRepository insuranceProductRepository;
    private final InsuranceAgentRepository insuranceAgentRepository; // 추가
    private final InsuranceCommissionPolicy insuranceCommissionPolicy;
    private final KafkaEventPublisher kafkaEventPublisher;

    // Create
    public Long createInsuranceApplication(InsuranceDto insuranceDto) {
        InsuranceProduct product = insuranceProductRepository.findById(insuranceDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Insurance product not found"));

        InsuranceOffer insuranceOffer = InsuranceOffer.builder()
                .product(product)
                .mainContractPremium(insuranceDto.getMainContractPremium())
                .status(InsuranceStatus.APPLIED)
                .build();

        return insuranceOfferRepository.save(insuranceOffer).getId();
    }

    // Read
    public InsuranceDto getInsuranceApplication(Long insuranceId) {
        InsuranceOffer insuranceOffer = insuranceOfferRepository.findById(insuranceId)
                .orElseThrow(() -> new IllegalArgumentException("Insurance not found"));

        return convertToDto(insuranceOffer);
    }

    // Update
    public void updateInsuranceApplication(Long insuranceId, InsuranceDto insuranceDto) {
        InsuranceOffer insuranceOffer = insuranceOfferRepository.findById(insuranceId)
                .orElseThrow(() -> new IllegalArgumentException("Insurance not found"));

        InsuranceProduct product = insuranceProductRepository.findById(insuranceDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Insurance product not found"));
        insuranceOffer.setProduct(product);
        insuranceOffer.setMainContractPremium(insuranceDto.getMainContractPremium());


        insuranceOfferRepository.save(insuranceOffer);
    }

    // Delete
    public void deleteInsuranceApplication(Long insuranceId) {
        insuranceOfferRepository.deleteById(insuranceId);
    }

    // Helper method to convert entity to DTO
    private InsuranceDto convertToDto(InsuranceOffer insuranceOffer) {
        Long commission = 0L;
        if (insuranceOffer.getStatus() == InsuranceStatus.ACCEPTED) {
            InsuranceCommission insuranceCommission = insuranceCommissionRepository.findByInsurance(insuranceOffer);
            if (insuranceCommission != null) {
                commission = insuranceCommission.getAmount();
            }
        }

        // 설계사, 계약자, 피보험자 이름 가져오는 부분 추가 (예시 - 실제 로직은 다를 수 있음)
        String applicantName = "계약자 이름"; // TODO: 실제 계약자 이름 가져오는 로직 구현
        String insuredPersonName = "피보험자 이름"; // TODO: 실제 피보험자 이름 가져오는 로직 구현
        String insuranceAgentName = insuranceOffer.getStatus() == InsuranceStatus.ACCEPTED ?
                insuranceCommissionRepository.findByInsurance(insuranceOffer) : "설계사 이름";


        return InsuranceDto.builder()
                .insuranceId(insuranceOffer.getId())
                .productId(insuranceOffer.getProduct().getId())
                .productName(insuranceOffer.getProduct().getName())
                .mainContractPremium(insuranceOffer.getMainContractPremium())
                .commission(commission)
                .applicantName(applicantName)
                .insuredPersonName(insuredPersonName)
                .insuranceAgentName(insuranceAgentName)
                .status(insuranceOffer.getStatus())
                .build();
    }

    // 기존 acceptInsuranceApplication 메서드
    public InsuranceDto acceptInsuranceApplication(Long insuranceId, Long agentId) { // 설계사 ID 추가
        InsuranceOffer insuranceOffer = insuranceOfferRepository.findById(insuranceId)
                .orElseThrow(() -> new IllegalArgumentException("Insurance not found"));

        insuranceOffer.setStatus(InsuranceStatus.ACCEPTED);

        long commission = insuranceCommissionPolicy.calculateCommission(insuranceOffer.getMainContractPremium());

        InsuranceAgent agent = insuranceAgentRepository.findById(agentId)
                .orElseThrow(() -> new IllegalArgumentException("Agent not found"));

        InsuranceCommission insuranceCommission = InsuranceCommission.builder()
                .insuranceOffer(insuranceOffer)
                .agent(agent) // 설계사 설정
                .amount(commission)
                .build();
        insuranceCommissionRepository.save(insuranceCommission);

        String applicantName = "계약자 이름"; // TODO: 실제 계약자 이름 가져오는 로직 구현
        String insuredPersonName = "피보험자 이름"; // TODO: 실제 피보험자 이름 가져오는 로직 구현
        String insuranceAgentName = agent.getName(); // 설계사 이름


        kafkaEventPublisher.publish(new InsuranceApplicationAcceptedEvent(
                insuranceId,
                commission,
                insuranceOffer.getProduct().getName(),
                insuranceAgentName,
                applicantName,
                insuredPersonName
        ));

        return convertToDto(insuranceOffer);
    }
}