-- data.sql

-- InsuranceProduct (MetLife sample products)
INSERT INTO insurance_product (name, created_at, updated_at)
VALUES ('(무)메트라이프 변액유니버셜 GI보험(2종)', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('(무)메트라이프 실버케어보험', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('(무)메트라이프 생활보장보험', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('(무)메트라이프 The One 종신보험', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- InsuranceAgent (Sample Agents)
INSERT INTO insurance_agent (name, code, created_at, updated_at)
VALUES ('Agent Kim', 'A001', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('Agent Lee', 'A002', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('Agent Park', 'A003', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
-- Added a dummy code to avoid null

-- Applicant (Sample Applicants)
INSERT INTO applicant (name, age, created_at, updated_at)
VALUES ('John Doe', 30, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('Jane Smith', 45, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('Peter Jones', 25, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- InsuredPerson (Sample Insured Persons)
INSERT INTO insured_person (name, age, job_code, created_at, updated_at)
VALUES -- Removed insurance_offer_id initially
       ('John Doe', 30, 101, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('Jane Smith', 45, 202, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       ('Emily Davis', 28, 101, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- InsuranceOffer (Sample Offers)
INSERT INTO insurance_offer (product_id, main_contract_premium, status, agent_id, applicant_id, insured_person_id,
                             created_at, updated_at)
VALUES (1, 100000, 'APPLIED', 1, 1, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       (2, 50000, 'ACCEPTED', 2, 2, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       (3, 75000, 'REJECTED', 1, 3, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
-- Explicitly set insured_person_id to null where needed


-- InsuranceCommission (Sample Commissions)
INSERT INTO insurance_commission (insurance_offer_id, agent_id, amount, created_at, updated_at)
VALUES (1, 1, 10000, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
       (2, 2, 5000, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());