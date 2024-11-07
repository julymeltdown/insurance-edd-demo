package com.lhs.insurance.domain.entity;

import com.lhs.insurance.domain.entity.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceOffer extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private InsuranceProduct product; // 보험 상품

    private Long mainContractPremium;
    @Enumerated(EnumType.STRING)
    private InsuranceStatus status;

    @OneToOne(mappedBy = "insuranceOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    private InsuranceCommission commission;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private InsuranceAgent agent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insured_person_id")
    private InsuredPerson insuredPerson;
}
