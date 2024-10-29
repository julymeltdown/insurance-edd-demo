package com.lhs.insurance.domain.repository;

import com.lhs.insurance.domain.entity.InsuranceCommission;
import com.lhs.insurance.domain.entity.InsuranceOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsuranceCommissionRepository extends JpaRepository<InsuranceCommission, Long> {
    Optional<InsuranceCommission> findByInsuranceOffer(InsuranceOffer insuranceOffer);
}
