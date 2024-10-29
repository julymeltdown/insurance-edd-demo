CREATE TABLE insurance_product
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE insurance_agent
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255),
    code       VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE applicant
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255),
    age        INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);


CREATE TABLE insurance_offer
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id            BIGINT,
    main_contract_premium BIGINT,
    status                VARCHAR(255),
    agent_id              BIGINT,
    applicant_id          BIGINT,
    insured_person_id     BIGINT,
    created_at            TIMESTAMP,
    updated_at            TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES insurance_product (id),
    FOREIGN KEY (agent_id) REFERENCES insurance_agent (id),
    FOREIGN KEY (applicant_id) REFERENCES applicant (id)
);


CREATE TABLE insured_person
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(255),
    age                INT,
    job_code           INT,
    insurance_offer_id BIGINT,
    created_at         TIMESTAMP,
    updated_at         TIMESTAMP,
    FOREIGN KEY (insurance_offer_id) REFERENCES insurance_offer (id)
);

ALTER TABLE insurance_offer
    ADD FOREIGN KEY (insured_person_id) REFERENCES insured_person (id);

CREATE TABLE insurance_commission
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    insurance_offer_id BIGINT,
    agent_id           BIGINT,
    amount             BIGINT,
    created_at         TIMESTAMP,
    updated_at         TIMESTAMP,
    FOREIGN KEY (insurance_offer_id) REFERENCES insurance_offer (id),
    FOREIGN KEY (agent_id) REFERENCES insurance_agent (id)
);