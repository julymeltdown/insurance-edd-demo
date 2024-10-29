package com.lhs.insurance.domain.repository;

import com.lhs.insurance.domain.entity.InsuranceOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceOfferRepository extends JpaRepository<InsuranceOffer, Long> {
}
