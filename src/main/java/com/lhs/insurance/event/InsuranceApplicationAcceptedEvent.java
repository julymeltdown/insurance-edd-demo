package com.lhs.insurance.event;

import com.lhs.insurance.domain.entity.InsuranceAgent;
import com.lhs.insurance.domain.entity.InsuranceOffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class InsuranceApplicationAcceptedEvent{
    InsuranceOffer insuranceOffer;
    InsuranceAgent insuranceAgent;
    long commission;
}