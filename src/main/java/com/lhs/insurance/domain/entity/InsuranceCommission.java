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
public class InsuranceCommission extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_offer_id") //JoinColumn 추가
    private InsuranceOffer insuranceOffer;   // 관련 보험

    //설계사 정보 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private InsuranceAgent agent; // 설계사

    private Long amount; // 커미션 금액
}