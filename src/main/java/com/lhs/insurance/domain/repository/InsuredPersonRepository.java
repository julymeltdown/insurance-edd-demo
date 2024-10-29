package com.lhs.insurance.domain.repository;

import com.lhs.insurance.domain.entity.InsuranceOffer;
import com.lhs.insurance.domain.entity.InsuredPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsuredPersonRepository extends JpaRepository<InsuredPerson, Long> {
    Optional<InsuredPerson> findByInsurance(InsuranceOffer insuranceOffer);
}
