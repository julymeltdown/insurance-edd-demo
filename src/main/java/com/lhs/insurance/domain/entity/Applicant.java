package com.lhs.insurance.domain.entity;

import com.lhs.insurance.domain.entity.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Applicant.java (계약자 엔티티 - 예시)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Applicant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long age;
}