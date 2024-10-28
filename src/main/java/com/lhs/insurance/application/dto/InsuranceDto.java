package com.lhs.insurance.application.dto;

import lombok.Data;

@Data
public class InsuranceDto {
    private Long insuranceId;
    private String productName;  // 보험 상품 이름
    private Long mainContractPremium;
    private Long commission;
}
