package com.lhs.insurance.domain.repository;

import com.lhs.insurance.domain.entity.Applicant;
import com.lhs.insurance.domain.entity.InsuranceOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Optional<Applicant> findByInsurance(InsuranceOffer insuranceOffer);
}
