package com.lhs.insurance.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class One360HealthUpdateMessage {
    private Long insuranceOfferId;
    private Long mainContractPremium;
    private Long ApplicantId;
}
