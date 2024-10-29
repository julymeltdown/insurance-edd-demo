package com.lhs.insurance.domain.entity;

import com.lhs.insurance.domain.entity.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceAgent extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code; // 설계사 코드
    // 필요한 다른 필드 추가 (예: 소속, 연락처 등)
}