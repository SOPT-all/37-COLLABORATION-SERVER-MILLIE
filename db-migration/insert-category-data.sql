USE millie;

SET NAMES utf8mb4;
SET character_set_client = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results = utf8mb4;

INSERT INTO Category (title, description, category_image_key)
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
