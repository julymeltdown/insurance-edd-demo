package com.lhs.insurance.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InsuranceCommissionPaymentMessage {
    private Long agentId;
    private long commissionAmount;
}
