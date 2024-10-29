package com.lhs.insurance.domain.repository;

import com.lhs.insurance.domain.entity.InsuranceCommission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceCommissionRepository extends JpaRepository<InsuranceCommission, Long> {
}
