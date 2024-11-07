package com.lhs.insurance.application;

import com.lhs.insurance.domain.entity.*;
import com.lhs.insurance.domain.repository.*;
import com.lhs.insurance.event.InsuranceApplicationAcceptedEvent;
import com.lhs.insurance.exception.*;
import com.lhs.insurance.infrastructure.InsuranceCommissionPolicy;
import com.lhs.insurance.presentation.request.InsuranceAcceptRequestDto;
import com.lhs.insurance.presentation.request.InsuranceCreateRequestDto;
import com.lhs.insurance.presentation.response.InsuranceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final InsuranceCommissionPolicy insuranceCommissionPolicy;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public InsuranceResponseDto createInsuranceOffer(InsuranceCreateRequestDto requestDto) {
        InsuranceOffer insuranceOffer = createInsuranceOfferEntity(requestDto);
        insuranceOfferRepository.save(insuranceOffer);
        return convertToDto(insuranceOffer);
    }

    private InsuranceOffer createInsuranceOfferEntity(InsuranceCreateRequestDto requestDto) {
        Entities entities = fetchRelatedEntities(requestDto);

        long commissionAmount = insuranceCommissionPolicy.calculateCommission(requestDto.getMainContractPremium());

        InsuranceCommission commission = InsuranceCommission.builder()
                .agent(entities.agent)
                .amount(commissionAmount)
                .build();

        InsuranceOffer insuranceOffer = InsuranceOffer.builder()
                .product(entities.product)
                .mainContractPremium(requestDto.getMainContractPremium())
                .status(InsuranceStatus.APPLIED)
                .applicant(entities.applicant)
                .insuredPerson(entities.insuredPerson)
                .agent(entities.agent)
                .commission(commission)
                .build();

        commission.setInsuranceOffer(insuranceOffer);
        return insuranceOffer;
    }

    @Transactional
    public InsuranceResponseDto acceptInsuranceOffer(InsuranceAcceptRequestDto requestDto) {
        InsuranceOffer insuranceOffer = insuranceOfferRepository.findById(requestDto.getInsuranceOfferId())
                .orElseThrow(() -> new InsuranceOfferNotFoundException("Insurance offer not found with ID: " + requestDto.getInsuranceOfferId()));

        insuranceOffer.setStatus(InsuranceStatus.ACCEPTED);
        publishInsuranceAcceptedEvent(insuranceOffer);

        return convertToDto(insuranceOffer);
    }


    private void publishInsuranceAcceptedEvent(InsuranceOffer insuranceOffer) {
        InsuranceApplicationAcceptedEvent event = new InsuranceApplicationAcceptedEvent(
                insuranceOffer, insuranceOffer.getAgent(), insuranceOffer.getCommission().getAmount()
        );
        applicationEventPublisher.publishEvent(event);
    }


    private InsuranceResponseDto convertToDto(InsuranceOffer insuranceOffer) {
        Long commissionAmount = (insuranceOffer.getCommission() != null) ?
                insuranceOffer.getCommission().getAmount() : null;

        return InsuranceResponseDto.builder()
                .id(insuranceOffer.getId())
                .productId(insuranceOffer.getProduct().getId())
                .productName(insuranceOffer.getProduct().getName())
                .mainContractPremium(insuranceOffer.getMainContractPremium())
                .status(insuranceOffer.getStatus())
                .applicantName(insuranceOffer.getApplicant().getName())
                .insuredPersonName(insuranceOffer.getInsuredPerson().getName())
                .insuranceAgentName(insuranceOffer.getAgent().getName())
                .commissionAmount(commissionAmount)
                .build();
    }


    @Transactional(readOnly = true)
    public InsuranceResponseDto retrieveInsuranceOffer(Long insuranceOfferId) {
        InsuranceOffer insuranceOffer = insuranceOfferRepository.findById(insuranceOfferId)
                .orElseThrow(() -> new InsuranceOfferNotFoundException("Insurance offer not found with ID: " + insuranceOfferId));

        return convertToDto(insuranceOffer);
    }

    private Entities fetchRelatedEntities(InsuranceCreateRequestDto requestDto) {
        InsuranceProduct product = insuranceProductRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + requestDto.getProductId()));
        Applicant applicant = applicantRepository.findById(requestDto.getApplicantId())
                .orElseThrow(() -> new ApplicantNotFoundException("Applicant not found with ID: " + requestDto.getApplicantId()));
        InsuredPerson insuredPerson = insuredPersonRepository.findById(requestDto.getInsuredPersonId())
                .orElseThrow(() -> new InsuredPersonNotFoundException("Insured person not found with ID:" + requestDto.getInsuredPersonId()));
        InsuranceAgent agent = insuranceAgentRepository.findById(requestDto.getAgentId())
                .orElseThrow(() -> new InsuranceAgentNotFoundException("Agent not found with ID: " + requestDto.getAgentId()));

        return new Entities(product, applicant, insuredPerson, agent);
    }

    private record Entities(InsuranceProduct product, Applicant applicant,
                            InsuredPerson insuredPerson, InsuranceAgent agent) {
    }
}