package com.lhs.insurance.application;

import com.lhs.insurance.domain.entity.*;
import com.lhs.insurance.domain.repository.*;
import com.lhs.insurance.event.InsuranceApplicationAcceptedEvent;
import com.lhs.insurance.infrastructure.InsuranceCommissionPolicy;
import com.lhs.insurance.infrastructure.KafkaEventPublisher;
import com.lhs.insurance.presentation.request.InsuranceAcceptRequestDto;
import com.lhs.insurance.presentation.request.InsuranceCreateRequestDto;
import com.lhs.insurance.presentation.response.InsuranceResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceOfferRepository insuranceOfferRepository;
    private final InsuranceProductRepository insuranceProductRepository;
    private final ApplicantRepository applicantRepository;
    private final InsuredPersonRepository insuredPersonRepository;
    private final InsuranceAgentRepository insuranceAgentRepository;
    private final InsuranceCommissionRepository insuranceCommissionRepository;
    private final InsuranceCommissionPolicy insuranceCommissionPolicy; // 수수료 계산 정책
    private final KafkaEventPublisher kafkaEventPublisher;


    @Transactional
    public InsuranceResponseDto createInsuranceOffer(InsuranceCreateRequestDto requestDto) {
        // 상품, 계약자, 피보험자, 설계사 정보 조회
        // TODO: restcontrolleradvice 사용하도록 리팩토링 필요
        InsuranceProduct product = insuranceProductRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Applicant applicant = applicantRepository.findById(requestDto.getApplicantId())
                .orElseThrow(() -> new EntityNotFoundException("Applicant not found"));
        InsuredPerson insuredPerson = insuredPersonRepository.findById(requestDto.getInsuredPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Insured person not found"));
        InsuranceAgent agent = insuranceAgentRepository.findById(requestDto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found"));

        long commissionAmount = insuranceCommissionPolicy.calculateCommission(requestDto.getMainContractPremium());

        InsuranceCommission commission = InsuranceCommission.builder()
                .agent(agent)
                .amount(commissionAmount)
                .build();

        InsuranceOffer insuranceOffer = InsuranceOffer.builder()
                .product(product)
                .mainContractPremium(requestDto.getMainContractPremium())
                .status(InsuranceStatus.APPLIED)
                .applicant(applicant)
                .insuredPerson(insuredPerson)
                .agent(agent)
                .commission(commission)
                .build();

        commission.setInsuranceOffer(insuranceOffer);


        insuranceOfferRepository.save(insuranceOffer);

        return convertToDto(insuranceOffer);
    }


    @Transactional
    public InsuranceResponseDto acceptInsuranceOffer(InsuranceAcceptRequestDto requestDto) {
        InsuranceOffer insuranceOffer = insuranceOfferRepository.findById(requestDto.getInsuranceOfferId())
                .orElseThrow(() -> new EntityNotFoundException("Insurance offer not found"));

        // 상태 변경 (ACCEPTED)
        insuranceOffer.setStatus(InsuranceStatus.ACCEPTED);

        // 수수료 계산 및 저장
        long commissionAmount = insuranceCommissionPolicy.calculateCommission(insuranceOffer.getMainContractPremium());
        InsuranceCommission commission = InsuranceCommission.builder()
                .insuranceOffer(insuranceOffer)
                .agent(insuranceOffer.getAgent()) // agent 정보 설정
                .amount(commissionAmount)
                .build();
        insuranceCommissionRepository.save(commission);
        insuranceOffer.setCommission(commission); // 양방향 관계 설정


        // 이벤트 발행
        InsuranceApplicationAcceptedEvent event = new InsuranceApplicationAcceptedEvent(
                insuranceOffer.getId(),
                commission.getAmount(),
                insuranceOffer.getProduct().getName(),
                insuranceOffer.getAgent().getName(),
                insuranceOffer.getApplicant().getName(),
                insuranceOffer.getInsuredPerson().getName()
        );
        kafkaEventPublisher.publish(event);


        return convertToDto(insuranceOffer);
    }


    private InsuranceResponseDto convertToDto(InsuranceOffer insuranceOffer) {
        return InsuranceResponseDto.builder()
                .id(insuranceOffer.getId())
                .productId(insuranceOffer.getProduct().getId())
                .productName(insuranceOffer.getProduct().getName())
                .mainContractPremium(insuranceOffer.getMainContractPremium())
                .status(insuranceOffer.getStatus())
                .applicantName(insuranceOffer.getApplicant().getName())
                .insuredPersonName(insuranceOffer.getInsuredPerson().getName())
                .insuranceAgentName(insuranceOffer.getAgent().getName())
                .commissionAmount(insuranceOffer.getCommission() != null ? insuranceOffer.getCommission().getAmount() : null) // 수수료 금액 추가
                .build();
    }


    @Transactional(readOnly = true)
    public InsuranceResponseDto retrieveInsuranceOffer(Long insuranceOfferId) {
        InsuranceOffer insuranceOffer = insuranceOfferRepository.findById(insuranceOfferId)
                .orElseThrow(() -> new EntityNotFoundException("Insurance offer not found"));

        return convertToDto(insuranceOffer);
    }
}