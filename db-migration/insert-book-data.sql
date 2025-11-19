USE millie;

SET NAMES utf8mb4;
SET character_set_client = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results = utf8mb4;

INSERT INTO book (title, author_name, novel_type, published_date, rating, full_read_rate, completion_time, is_audiobook, book_image_key, introduce, voice_actor)
VALUES
    ('홍학의 자리', '정해연', '소설', '2021-07-21', 3.9, 80, 247, 0, 'images/book-covers/BOOK_IMAGE_1.png', '"이 행복이 영원할 거라고 생각한 적은 없었다. 그러나 이런 끝을 상상한 적도 없었다."예측 불가! 한국 미스터리 사상 전무후무한 반전!', NULL),
    ('홍학의 자리', '정해연', '소설', '2021-07-21', 3.9, 80, 247, 1, 'images/book-covers/BOOK_IMAGE_2.png', '오디오북으로 만나는 한국 미스터리의 걸작. 임은지 성우의 섬세한 연기로 더욱 생생하게 전해지는 긴장감 넘치는 이야기.', '임은지'),
    ('자연의 가장자리와 자연사', '신해욱', '시/에세이', '2024-08-01', 3.6, 57, 481, 0, 'images/book-covers/BOOK_IMAGE_3.png', '자연의 경계에서 발견한 삶의 본질. 철학자의 시선으로 자연과 인간의 관계를 새롭게 조명하는 깊이 있는 에세이.', NULL),
    ('신뢰의 자리', '김만성', '종교', '2021-11-25', 3.5, 67, 231, 0, 'images/book-covers/BOOK_IMAGE_4.png', '흔들리는 시대, 변하지 않는 믿음의 가치. 일상 속에서 신뢰를 회복하고 더 나은 관계를 만들어가는 지혜를 담았다.', NULL),
    ('모든 것의 가장자리에서', '파커 J. 파머', '인문', '2018-07-27', 4.1, 78, 319, 0, 'images/book-covers/BOOK_IMAGE_5.png', '삶의 경계에서 마주하는 성찰의 시간. 교육자이자 작가인 저자가 전하는 진정한 자아 찾기와 공동체의 의미.', NULL),
    ('빈자리', '크리스티앙 보뱅', '시/에세이', '2015-02-15', 3.6, 64, 381, 0, 'images/book-covers/BOOK_IMAGE_6.png', '상실의 공간에서 발견하는 충만함. 프랑스 작가 특유의 섬세한 문체로 그려낸 부재와 존재에 대한 시적 명상.', NULL),
    ('당신의 자리-나무로 자라는', '유희경', '시/에세이', '2018-09-10', 4.1, 73, 192, 0, 'images/book-covers/BOOK_IMAGE_7.png', '각자의 자리에서 피어나는 삶의 이야기. 나무처럼 단단히 뿌리내리고 하늘을 향해 성장하는 우리의 여정을 그린다.', NULL),
    ('헌법의 자리', '박한철', '인문', '2022-09-26', 3.4, 63, 438, 0, 'images/book-covers/BOOK_IMAGE_8.png', '헌법재판소 전 소장이 들려주는 헌법 이야기. 우리 삶 속에서 헌법이 차지하는 위치와 그 가치를 쉽고 명쾌하게 풀어낸다.', NULL);
