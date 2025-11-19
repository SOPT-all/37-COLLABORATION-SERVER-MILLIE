USE millie;

SET NAMES utf8mb4;
SET character_set_client = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results = utf8mb4;

INSERT INTO book (title, author_name, novel_type, published_date, rating, full_read_rate, completion_time, is_audiobook, book_image_key, introduce, voice_actor)
VALUES
    ('홍학의 자리', '정해연', '소설', '2021-07-21', 3.9, 80, 247, 0, 'images/book-covers/BOOK_IMAGE_1.png', '“이 행복이 영원할 거라고 생각한 적은 없었다. 그러나 이런 끝을 상상한 적도 없었다.”예측 불가! 한국 미스터리 사상 전무후무한 반전!', NULL),
    ('홍학의 자리', '정해연', '소설', '2021-07-21', 3.9, 80, 247, 1, 'images/book-covers/BOOK_IMAGE_2.png', NULL, '임은지'),
    ('자연의 가장자리와 자연사', '신해욱', '시/에세이', '2024-08-01', 3.6, 57, 481, 0, 'images/book-covers/BOOK_IMAGE_3.png', NULL, NULL),
    ('신뢰의 자리', '김만성', '종교', '2021-11-25', 3.5, 67, 231, 0, 'images/book-covers/BOOK_IMAGE_4.png', NULL, NULL),
    ('모든 것의 가장자리에서', '파커 J. 파머', '인문', '2018-07-27', 4.1, 78, 319, 0, 'images/book-covers/BOOK_IMAGE_5.png', NULL, NULL),
    ('빈자리', '크리스티앙 보뱅', '시/에세이', '2015-02-15', 3.6, 64, 381, 0, 'images/book-covers/BOOK_IMAGE_6.png', NULL, NULL),
    ('당신의 자리-나무로 자라는', '유희경', '시/에세이', '2018-09-10', 4.1, 73, 192, 0, 'images/book-covers/BOOK_IMAGE_7.png', NULL, NULL),
    ('헌법의 자리', '박한철', '인문', '2022-09-26', 3.4, 63, 438, 0, 'images/book-covers/BOOK_IMAGE_8.png', NULL, NULL);
