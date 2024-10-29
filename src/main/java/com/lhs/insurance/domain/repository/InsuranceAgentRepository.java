package com.lhs.insurance.domain.repository;

import com.lhs.insurance.domain.entity.InsuranceAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceAgentRepository extends JpaRepository<InsuranceAgent, Long> {
}
