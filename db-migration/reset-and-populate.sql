-- =====================================================
-- DB 초기화 및 데이터 재삽입 통합 스크립트
-- =====================================================
-- 주의: 이 스크립트는 기존 데이터를 모두 삭제합니다!
-- =====================================================

USE millie;

-- UTF-8 인코딩 설정
SET NAMES utf8mb4;
SET character_set_client = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results = utf8mb4;

-- 외래 키 체크 비활성화 (삭제 순서 무관하게 처리)
SET FOREIGN_KEY_CHECKS = 0;

-- =====================================================
-- 1단계: 기존 데이터 삭제
-- =====================================================
SELECT '=== 기존 데이터 삭제 시작 ===' AS message;

TRUNCATE TABLE review;
SELECT '- Review 테이블 초기화 완료' AS message;

TRUNCATE TABLE banner;
SELECT '- Banner 테이블 초기화 완료' AS message;

TRUNCATE TABLE category;
SELECT '- Category 테이블 초기화 완료' AS message;

TRUNCATE TABLE book;
SELECT '- Book 테이블 초기화 완료' AS message;

SELECT '=== 기존 데이터 삭제 완료 ===' AS message;

-- =====================================================
-- 2단계: 새로운 데이터 삽입
-- =====================================================
SELECT '=== 새로운 데이터 삽입 시작 ===' AS message;

-- Book 데이터 삽입
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

SELECT '- Book 데이터 삽입 완료 (8건)' AS message;

-- Category 데이터 삽입
INSERT INTO category (title, description, category_image_key)
VALUES
    ('소설', '추리/스릴러,킬러 스파이,법의학 스릴러,SF,판타지 등', 'images/category-images/CATEGORY-IMAGE-NOVEL.png'),
    ('세계문학전집', '열린책들,민음사,현대지성,을유문화사,살림,휴머니스트,문예출판사 등', 'images/category-images/CATEGORY-IMAGE-WORLD-LITERATURE.png'),
    ('어린이', '그림책/동화책,유아 학습,어린이 학습,초등 저학년,초등 고학년,학습 만화 등', 'images/category-images/CATEGORY-IMAGE-CHILDREN.png'),
    ('청소년', '문학,학습법,인문/교양,수학/과학,외국어', 'images/category-images/CATEGORY-IMAGE-YOUTH.png'),
    ('만화', '인문/교양,일상/드라마,어린이/청소년,그래픽노블', 'images/category-images/CATEGORY-IMAGE-COMICS.png'),
    ('에세이/시', '시,일상,위로,직업,관계,여행,예술,자연,사랑,가족,나이듦 등', 'images/category-images/CATEGORY-IMAGE-ESSAY-POETRY.png'),
    ('경제경영', '4차 산업혁명,IT,마케팅,세계 경제,한국 경제,경제학,기업&CEO,경영,부자 등', 'images/category-images/CATEGORY-IMAGE-BUSINESS-ECONOMICS.png'),
    ('자기계발', '성공,말하기/협상,프레젠테이션,시간 관리,습관,글쓰기,독서 등', 'images/category-images/CATEGORY-IMAGE-SELF-DEVELOPMENT.png'),
    ('외국어', '영어독해/문법/작문,영어어휘,영어회화,영어일반,수험영어,일본어,중국어 등', 'images/category-images/CATEGORY-IMAGE-FOREIGN-LANGUAGE.png'),
    ('인문', '인문학,문명,문화,심리학,독서/공부,강의,글쓰기,인간/인류,신화,언어,사랑 등', 'images/category-images/CATEGORY-IMAGE-HUMANITIES.png'),
    ('IT', '개발/프로그래밍,IT 교양,그래픽/멀티미디어,e비즈니스 등', 'images/category-images/CATEGORY-IMAGE-IT.png'),
    ('역사', '한국 고대사,조선사,한국 근현대사,세계사,역사 소설', 'images/category-images/CATEGORY-IMAGE-HISTORY.png'),
    ('종교', '기독교(개신교),학술 연구,손안의 성경,기도/묵상,불교,카톨릭,주역/기타', 'images/category-images/CATEGORY-IMAGE-RELIGION.png'),
    ('여행', '여행 에세이,한국,제주도,중국,일본,대만/홍콩,유럽,동남아,미국,호주 등', 'images/category-images/CATEGORY-IMAGE-TRAVEL.png'),
    ('매거진', '경제/경영,패션/트렌드,대중문화,인문/예술,리빙/라이프스타일 등', 'images/category-images/CATEGORY-IMAGE-MAGAZINE.png'),
    ('디즈니', '디즈니,픽사,마블의 책이 보이는 영어 오디오북 ©Disney', 'images/category-images/CATEGORY-IMAGE-DISNEY.png'),
    ('빨간펜 동화', '아이도, 어른도 함께 즐기는 동화의 시간!', 'images/category-images/CATEGORY-IMAGE-RED-PEN-FAIRY-TALE.png'),
    ('밀리 오리지널', '밀리가 기획하고 발굴한, 밀리만의 책', 'images/category-images/CATEGORY-IMAGE-MILLI-ORIGINAL.png'),
    ('독립출판', '기획부터 출판까지, 내손으로 직접 만든 개성 있는 책', 'images/category-images/CATEGORY-IMAGE-INDEPENDENT-PUBLISHING.png'),
    ('오디오북', '소설,인문,경제경영,자기계발,에세이,과학,사회,역사 등', 'images/category-images/CATEGORY-IMAGE-AUDIOBOOK.png'),
    ('챗북', '백발백중!별자리 운세,쇼트 클래식,인터뷰 시리즈,인문,사회,경제/경영,소설', 'images/category-images/CATEGORY-IMAGE-CHATBOOK.png'),
    ('도슨트북', '15분에 한 권씩! 핵심 인사이트를 요약한 책', 'images/category-images/CATEGORY-IMAGE-DOCENT-BOOK.png'),
    ('오브제북', '활자를 넘어 아름다운 그림과 음악을 함께 감상하는 책', 'images/category-images/CATEGORY-IMAGE-OBJET-BOOK.png');

SELECT '- Category 데이터 삽입 완료 (23건)' AS message;

-- Banner 데이터 삽입
INSERT INTO banner (title, content, banner_image_key, book_id)
VALUES
    ('《홍학의 자리》 읽을 준비!', '이 책부터 읽어야 재미가 2배', 'images/banners/MAIN_BANNER.png', 1);

SELECT '- Banner 데이터 삽입 완료 (1건)' AS message;

-- Review 데이터 삽입
INSERT INTO review (book_id, reviewer_name, created_date, content, like_count, is_liked)
VALUES
  -- 홍학의 자리 (일반)
  (1, '마침표수집가', '2025.09.20', '이야기의 흐름보다 분위기와 감정이 중심이라 호불호는 있을 듯. 하지만 저는 좋았습니다.', 58, 0),
  (1, '소다맛마시멜로우', '2025.10.28', '하 읽은 사람들이랑 수다떨고싶다....', 126, 0),

  -- 홍학의 자리 (오디오북)
  (2, '책벌레89', '2025.11.02', '임은지 성우님 목소리가 정말 좋아요. 출퇴근길에 듣기 딱 좋았습니다.', 42, 0),
  (2, '오디오매니아', '2025.11.05', '원작도 재밌었는데 오디오북으로 들으니 또 다른 느낌! 강추합니다', 67, 0),

  -- 자연의 가장자리와 자연사
  (3, '산책하는사람', '2025.10.15', '자연에 대한 깊은 통찰이 담긴 책. 천천히 음미하며 읽기 좋아요.', 31, 0),
  (3, '철학도', '2025.10.22', '자연과 인간의 관계에 대해 다시 생각해보게 되는 책이네요', 24, 0),

  -- 신뢰의 자리
  (4, '믿음지킴이', '2025.09.30', '요즘 같은 시대에 꼭 필요한 메시지를 담고 있습니다.', 45, 0),
  (4, '평온한하루', '2025.10.12', '신뢰의 중요성을 다시 깨닫게 해주는 책입니다. 추천해요!', 38, 0),

  -- 모든 것의 가장자리에서
  (5, '내면탐험가', '2025.11.01', '파커 J. 파머의 통찰력이 빛나는 책. 진정한 자아를 찾는 여정이 감동적이에요.', 89, 0),
  (5, '성찰하는삶', '2025.11.08', '공동체와 개인의 균형에 대한 깊은 성찰. 여러 번 읽고 싶은 책', 72, 0),

  -- 빈자리
  (6, '시인의마음', '2025.10.18', '프랑스 작가 특유의 섬세함이 느껴집니다. 시적인 문장들이 아름다워요.', 56, 0),
  (6, '감성독서', '2025.10.25', '상실의 아픔을 이렇게 아름답게 표현할 수 있다니... 울컥했어요', 63, 0),

  -- 당신의 자리-나무로 자라는
  (7, '나무사랑', '2025.11.03', '나무처럼 살아간다는 것의 의미를 되새기게 됩니다. 좋은 책!', 51, 0),
  (7, '에세이러버', '2025.11.10', '짧지만 깊은 울림이 있는 에세이. 유희경 작가님 책 더 찾아봐야겠어요', 44, 0),

  -- 헌법의 자리
  (8, '법학도', '2025.10.20', '어려운 헌법을 이렇게 쉽게 설명해주시다니! 법대생 필독서', 78, 0),
  (8, '시민의식', '2025.11.06', '우리 삶 속의 헌법을 이해하는 데 큰 도움이 됐습니다.', 61, 0);

SELECT '- Review 데이터 삽입 완료 (16건)' AS message;

-- 외래 키 체크 재활성화
SET FOREIGN_KEY_CHECKS = 1;

SELECT '=== 모든 데이터 삽입 완료 ===' AS message;
SELECT '  - Book: 8건' AS summary;
SELECT '  - Category: 23건' AS summary;
SELECT '  - Banner: 1건' AS summary;
SELECT '  - Review: 16건' AS summary;

-- =====================================================
-- 3단계: 결과 확인
-- =====================================================
SELECT '=== 최종 데이터 확인 ===' AS message;

SELECT COUNT(*) AS book_count FROM book;
SELECT COUNT(*) AS review_count FROM review;
SELECT COUNT(*) AS banner_count FROM banner;
SELECT COUNT(*) AS category_count FROM category;

SELECT '=== 완료 ===' AS message;
