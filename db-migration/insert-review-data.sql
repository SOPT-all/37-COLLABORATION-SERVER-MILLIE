-- UTF-8 인코딩 설정
SET NAMES utf8mb4;
SET character_set_client = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results = utf8mb4;

-- Review 더미 데이터 삽입
INSERT INTO review (book_id, reviewer_name, created_date, content, like_count, is_liked)
VALUES
  (1, '마침표수집가', '2025.09.20', '이야기의 흐름보다 분위기와 감정이 중심이라 호불호는 있을 듯. 하지만 저는 좋았습니다.', 58, 0),
  (1, '소다맛마시멜로우', '2025.10.28', '하 읽은 사람들이랑 수다떨고싶다....', 126, 0);

-- 결과 확인
SELECT '=== 삽입 완료 ===' AS message;
SELECT * FROM review;
