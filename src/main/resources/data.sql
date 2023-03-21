--CREATE SCHEMA IF NOT EXISTS `rmdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin ;

USE `rmdb` ;

-- nostalgia_item
INSERT INTO nostalgia_item (title, img_url) VALUES ('도시락', 'https://user-images.githubusercontent.com/77563814/224518194-b45c1103-1de2-4e7d-9267-66ae4637c53a.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('못난이', 'https://user-images.githubusercontent.com/77563814/224518214-fd6cc6b1-b434-4401-aa24-be2b320bcb95.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('뽀빠이', 'https://user-images.githubusercontent.com/77563814/224518227-b0e1763f-4759-424b-86a7-eec9b6ec8b9d.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('하니만화', 'https://user-images.githubusercontent.com/77563814/224518255-7c3507a3-61b0-44a6-bea1-fba0ef1205b2.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('크레파스', 'https://user-images.githubusercontent.com/77563814/224518247-42e48290-c9e6-4746-9b4f-c9d650fc2682.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('사이다', 'https://user-images.githubusercontent.com/77563814/224518223-b9b2d0e1-8724-4394-b865-c4063a616d90.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('축음기', 'https://user-images.githubusercontent.com/77563814/224518244-556547ad-33d0-49cd-a326-414007ddfa5d.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('전화기', 'https://user-images.githubusercontent.com/77563814/224518236-d7b91ef0-6a23-4197-9922-2495c6e399e1.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('보석함', 'https://user-images.githubusercontent.com/77563814/224518233-1754aa30-3751-4b85-a54a-6141b24e44c9.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('빨래', 'https://user-images.githubusercontent.com/77563814/224518230-ad2ef68f-c6a9-44ad-bb27-5189d9338568.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('카세트', 'https://user-images.githubusercontent.com/77563814/224518246-69a88783-af07-4006-9b85-6a9cbcd25f16.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('공중전화', 'https://user-images.githubusercontent.com/77563814/224518191-e1692288-6a76-439e-a835-fb6d44ee32e4.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('사탕', 'https://user-images.githubusercontent.com/77563814/224518221-2d82e9b9-7ec0-4b2f-a513-c357a9321e03.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('옥수수빵', 'https://user-images.githubusercontent.com/77563814/224518219-0abc87f3-7467-438a-aaf9-ae1a8530279c.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('메주', 'https://user-images.githubusercontent.com/77563814/224518218-484d4dae-3a09-45a0-92af-7ac14373d945.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('게임기', 'https://user-images.githubusercontent.com/77563814/224518175-838008e0-b96d-4d84-a65a-8f67c2bab413.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('티비', 'https://user-images.githubusercontent.com/77563814/224518254-88cd4f06-fd0d-48f1-b7ae-ffb569b6cec9.png');
INSERT INTO nostalgia_item (title, img_url) VALUES ('타자기', 'https://user-images.githubusercontent.com/77563814/224518251-585c4708-6e9f-4b71-826a-a00c54bde45e.png');

-- 샘플 : Member1이 환자, Member2이 보호자 (Member1이과 2는 같은 가족)
-- Member
-- 비번은 다 '유저비번'
INSERT INTO member (member_id, username, password, phone, nickname, role, birth, gender, profile_img, address)
VALUES (1, '유저네임1', '$2a$10$DHVTiCR0UFOCptJNy9cVdOQuyGJbw9MRSjanBVxaR9jxAJ9RDlff2', '01099991111', '이름1', 'PATIENT', '1950-10-01', 'MALE', '이미지 주소', '주소');
INSERT INTO member (member_id, username, password, phone, nickname, role, birth, gender, profile_img, address)
VALUES (2, '유저네임2', '$2a$10$DHVTiCR0UFOCptJNy9cVdOQuyGJbw9MRSjanBVxaR9jxAJ9RDlff2', '01099991112', '이름2', 'CAREGIVER', '1950-10-01', 'MALE', '이미지 주소', '주소');
INSERT INTO member (member_id, username, password, phone, nickname, role, birth, gender, profile_img, address)
VALUES (3, '유저네임3', '$2a$10$DHVTiCR0UFOCptJNy9cVdOQuyGJbw9MRSjanBVxaR9jxAJ9RDlff2', '01099991113', '이름3', 'PATIENT', '1950-10-01', 'MALE', '이미지 주소', '주소');
INSERT INTO member (member_id, username, password, phone, nickname, role, birth, gender, profile_img, address)
VALUES (4, '유저네임4', '$2a$10$DHVTiCR0UFOCptJNy9cVdOQuyGJbw9MRSjanBVxaR9jxAJ9RDlff2', '01099991114', '이름4', 'PATIENT', '1950-10-01', 'MALE', '이미지 주소', '주소');
INSERT INTO member (member_id, username, password, phone, nickname, role, birth, gender, profile_img, address)
VALUES (5, '유저네임5', '$2a$10$DHVTiCR0UFOCptJNy9cVdOQuyGJbw9MRSjanBVxaR9jxAJ9RDlff2', '01099991115', '이름5', 'PATIENT', '1950-10-01', 'MALE', '이미지 주소', '주소');

-- Family
INSERT INTO family (family_id, patient_id) VALUES (1, 1);

-- family 관계
UPDATE member SET family_id = 1 WHERE member_id = 1;
UPDATE member SET family_id = 1 WHERE member_id = 2;

-- Memory
INSERT INTO Memory (memory_id, family_id, title, content, img_url)
VALUES (1, 1, "놀이 공원", "가족끼리 놀이 공원가서 재밌게 놀았었어요.", "https://images.unsplash.com/photo-1563564120768-da63ef4f7446?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1168&q=80");
INSERT INTO Memory (memory_id, family_id, title, content, img_url)
VALUES (2, 1, "부산 가족 여행", "가족끼리 부산 가서 재밌었던 기억이 있어요.", "https://images.unsplash.com/photo-1633335718204-670c3d4efe2d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80");
INSERT INTO Memory (memory_id, family_id, title, content, img_url)
VALUES (3, 1, "가족 제주 여행", "16년도 여름 제주 여행을 가족 모두 함께 갔었어요.", "https://images.unsplash.com/photo-1579169326371-ccb4e63f7889?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80");