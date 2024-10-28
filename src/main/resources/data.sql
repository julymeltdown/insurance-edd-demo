-- insurance_product 테이블
INSERT INTO insurance_product (name) VALUES ('동행');
INSERT INTO insurance_product (name) VALUES ('동행 Plus');
INSERT INTO insurance_product (name) VALUES ('백만종');
INSERT INTO insurance_product (name) VALUES ('360 간편');


-- member 테이블 (계약자) - 필요한 필드에 맞게 추가
INSERT INTO member (name, age, address, phone) VALUES ('이현수', 25, '경기도 고양시', '010-1234-5678');
INSERT INTO member (name, age, address, phone) VALUES ('춘배', 27, '경기도 성남시', '010-9876-5432');

-- insured_person 테이블 (피보험자) - 필요한 필드에 맞게 추가
INSERT INTO insured_person (name, age, relationship) VALUES ('이현수', 30, '본인');
INSERT INTO insured_person (name, age, relationship) VALUES ('보미', 25, '배우자');


-- insurance 테이블 (보험) - 필요한 필드에 맞게 추가. 외래키 관계 주의
INSERT INTO insurance (product_id, applicant_id, insured_person_id, main_contract_premium, status)
VALUES (1, 1, 1, 100000, 'APPLIED');
INSERT INTO insurance (product_id, applicant_id, insured_person_id, main_contract_premium, status)
VALUES (2, 2, 2, 50000, 'ACCEPTED');

-- insurance_commission 테이블 - 필요한 필드에 맞게 추가. 외래키 관계 주의.
INSERT INTO insurance_commission (insurance_id, amount) VALUES (2, 5000);