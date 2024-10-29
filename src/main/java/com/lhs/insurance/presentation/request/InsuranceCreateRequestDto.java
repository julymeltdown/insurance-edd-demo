package com.lhs.insurance.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceCreateRequestDto {
    private Long productId;
    private Long mainContractPremium;
    private Long applicantId;
    private Long insuredPersonId;
    private Long agentId;
}
