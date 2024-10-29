package com.lhs.insurance.infrastructure;

import org.springframework.stereotype.Component;

@Component
public class InsuranceCommissionPolicy {

    public long calculateCommission(long mainContractPremium) {
        return (long) (mainContractPremium * 0.1); // 주계약 보험료의 10% 계산
    }
}
