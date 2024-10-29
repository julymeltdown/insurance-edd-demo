package com.lhs.insurance.presentation.response;

import com.lhs.insurance.domain.entity.InsuranceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceResponseDto {
    private Long id;
    private Long productId;
    private String productName;
    private Long mainContractPremium;
    private InsuranceStatus status;
    private String applicantName;
    private String insuredPersonName;
    private String insuranceAgentName;
    private Long commissionAmount;  // 수수료 금액 추가
}
