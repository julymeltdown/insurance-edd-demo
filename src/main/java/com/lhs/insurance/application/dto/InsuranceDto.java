package com.lhs.insurance.application.dto;

import com.lhs.insurance.domain.entity.InsuranceStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceDto {
    private Long insuranceId; // 기존 insuranceId 유지
    private Long productId;
    private String productName;
    private Long mainContractPremium;
    private String applicantName;          // 계약자 이름
    private String insuredPersonName;      // 피보험자 이름
    private String insuranceAgentName;     //설계사 이름
    private Long applicantId;
    private Long insuredPersonId;
    private Long agentId;
    private InsuranceStatus status;
    private Long commissionAmount;
}