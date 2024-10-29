package com.lhs.insurance.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InsuranceApplicationAcceptedEvent {
    private Long insuranceId;
    private long commission;
    private String productName;
    private String insuranceAgentName; // 설계사 이름
    private String applicantName; // 계약자 이름
    private String insuredPersonName; // 피보험자 이름
}