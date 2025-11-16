# S3 이미지 관리 가이드

> API 개발 시 S3에 저장된 이미지를 PreSigned URL로 제공하는 방법

## 📋 개요

이 프로젝트는 이미지 파일을 AWS S3에 저장하고, API 응답에서 **PreSigned URL**을 통해 클라이언트에게 제공합니다.

- **S3 버킷**: `millie-collaboration-bucket`
- **리전**: `ap-northeast-2` (서울)
- **보안**: Private 버킷 + PreSigned URL (1시간 유효)
- **이미지 업로드**: 별도 관리 도구로 수동 업로드 (API 업로드 기능 없음)

---

## 🗂️ S3 폴더 구조 및 파일 명명 규칙

```
millie-collaboration-bucket/
├── images/
│   ├── book-covers/
│   │   ├── BOOK_IMAGE_1.jpg
│   │   ├── BOOK_IMAGE_2.jpg
│   │   └── BOOK_IMAGE_3.jpg
│   ├── book-category/
│   │   ├── CATEGORY_IMAGE_CARTOON.jpg
│   │   ├── CATEGORY_IMAGE_NOVEL.jpg
│   │   └── CATEGORY_IMAGE_ESSAY.jpg
│   └── banners/
│       ├── main-banner.jpg
│       └── event-banner.jpg
```

### 파일 명명 규칙

| 카테고리 | 경로 | 파일명 형식 | 예시 |
|---------|------|------------|------|
| 책 커버 이미지 | `images/book-covers/` | `BOOK_IMAGE_{책ID}.jpg` | `BOOK_IMAGE_1.jpg` |
| 카테고리 이미지 | `images/book-category/` | `CATEGORY_IMAGE_{카테고리명}.jpg` | `CATEGORY_IMAGE_CARTOON.jpg` |
| 배너 이미지 | `images/banners/` | `{용도}-banner.jpg` | `main-banner.jpg` |

---

## 💾 데이터베이스 저장 방식

### ⚠️ 중요: DB에는 S3 객체 키만 저장

**❌ 잘못된 방법** (URL 전체 저장)
```java
// 절대 이렇게 하지 마세요!
book.setCoverImageUrl("https://millie-collaboration-bucket.s3.ap-northeast-2.amazonaws.com/...");
```

**✅ 올바른 방법** (S3 객체 키만 저장)
```java
// DB에는 S3 키만 저장
book.setCoverImageKey("images/book-covers/BOOK_IMAGE_1.jpg");
```

### 이유
- PreSigned URL은 **1시간 후 만료**되므로 DB에 저장하면 안 됨
- S3 키는 변하지 않으므로 DB에 안전하게 저장 가능
- API 응답 시점에 매번 새로운 PreSigned URL 생성

---

## 🛠️ API 개발 방법

### 1. S3Service 주입

```java
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final S3Service s3Service;  // S3Service 주입

    // ...
}
```

### 2. 엔티티 설계

```java
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    // S3 객체 키만 저장 (URL 아님!)
    @Column(name = "cover_image_key")
    private String coverImageKey;  // 예: "images/book-covers/BOOK_IMAGE_1.jpg"

    // ...
}
```

### 3. DTO 설계

```java
public record BookResponse(
    Long id,
    String title,
    String coverImageUrl  // 클라이언트에게는 PreSigned URL 제공
) {
    public static BookResponse of(Book book, String coverImageUrl) {
        return new BookResponse(
            book.getId(),
            book.getTitle(),
            coverImageUrl
        );
    }
}
```

### 4. Service 로직

```java
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final S3Service s3Service;

    /**
     * 책 상세 조회
     */
    public BookResponse getBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new BaseException(BOOK_NOT_FOUND));

        // S3 키를 PreSigned URL로 변환
        String coverImageUrl = s3Service.generatePresignedUrlOrNull(book.getCoverImageKey());

        return BookResponse.of(book, coverImageUrl);
    }

    /**
     * 책 목록 조회
     */
    public List<BookResponse> getBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
            .map(book -> {
                String coverImageUrl = s3Service.generatePresignedUrlOrNull(book.getCoverImageKey());
                return BookResponse.of(book, coverImageUrl);
            })
            .toList();
    }
}
```

---

## 📖 S3Service API 레퍼런스

### `generatePresignedUrl(String objectKey)`

S3 객체 키를 받아서 1시간 유효한 PreSigned URL을 생성합니다.

**파라미터**:
- `objectKey` (String): S3 객체 키 (예: `"images/book-covers/BOOK_IMAGE_1.jpg"`)

**반환값**:
- `String`: PreSigned URL (1시간 유효)

**예외**:
- `objectKey`가 존재하지 않는 객체여도 URL은 생성됨 (접근 시 404 발생)

**사용 예시**:
```java
String imageKey = "images/book-covers/BOOK_IMAGE_1.jpg";
String imageUrl = s3Service.generatePresignedUrl(imageKey);
// 결과: https://millie-collaboration-bucket.s3.ap-northeast-2.amazonaws.com/images/book-covers/BOOK_IMAGE_1.jpg?X-Amz-Algorithm=...
```

### `generatePresignedUrlOrNull(String objectKey)`

S3 객체 키가 `null`이거나 빈 문자열인 경우 `null`을 반환합니다. (null-safe)

**파라미터**:
- `objectKey` (String): S3 객체 키 (nullable)

**반환값**:
- `String`: PreSigned URL 또는 `null`

**사용 예시**:
```java
// 책 커버가 없을 수도 있는 경우
String coverImageKey = book.getCoverImageKey();  // null일 수 있음
String coverImageUrl = s3Service.generatePresignedUrlOrNull(coverImageKey);
// coverImageKey가 null이면 coverImageUrl도 null
```

---

## 🎯 실전 예시

### 예시 1: 책 상세 조회 API

**엔티티**:
```java
Book book = Book.builder()
    .id(1L)
    .title("밀리의 서재")
    .coverImageKey("images/book-covers/BOOK_IMAGE_1.jpg")  // DB에 저장된 S3 키
    .build();
```

**Service**:
```java
public BookDetailResponse getBookDetail(Long bookId) {
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new BaseException(BOOK_NOT_FOUND));

    // S3 키 → PreSigned URL 변환
    String coverImageUrl = s3Service.generatePresignedUrl(book.getCoverImageKey());

    return BookDetailResponse.of(book, coverImageUrl);
}
```

**API 응답**:
```json
{
  "code": "S200",
  "message": "성공",
  "data": {
    "id": 1,
    "title": "밀리의 서재",
    "coverImageUrl": "https://millie-collaboration-bucket.s3.ap-northeast-2.amazonaws.com/images/book-covers/BOOK_IMAGE_1.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20251116T055000Z&..."
  }
}
```

### 예시 2: 카테고리별 책 목록 API

**Service**:
```java
public CategoryBooksResponse getCategoryBooks(String categoryName) {
    // 카테고리 이미지 키 생성
    String categoryImageKey = "images/book-category/CATEGORY_IMAGE_" + categoryName.toUpperCase() + ".jpg";
    String categoryImageUrl = s3Service.generatePresignedUrl(categoryImageKey);

    // 책 목록 조회
    List<Book> books = bookRepository.findByCategoryName(categoryName);
    List<BookResponse> bookResponses = books.stream()
        .map(book -> {
            String coverUrl = s3Service.generatePresignedUrlOrNull(book.getCoverImageKey());
            return BookResponse.of(book, coverUrl);
        })
        .toList();

    return new CategoryBooksResponse(categoryName, categoryImageUrl, bookResponses);
}
```

**API 응답**:
```json
{
  "code": "S200",
  "message": "성공",
  "data": {
    "categoryName": "CARTOON",
    "categoryImageUrl": "https://millie-collaboration-bucket.s3.ap-northeast-2.amazonaws.com/images/book-category/CATEGORY_IMAGE_CARTOON.jpg?...",
    "books": [
      {
        "id": 1,
        "title": "만화책 1",
        "coverImageUrl": "https://millie-collaboration-bucket.s3.ap-northeast-2.amazonaws.com/images/book-covers/BOOK_IMAGE_1.jpg?..."
      }
    ]
  }
}
```

---

## ⚠️ 주의사항

### 1. PreSigned URL 유효기간

- **유효시간**: 1시간
- **만료 후**: 403 Forbidden 에러 발생
- **해결책**: 클라이언트가 만료된 URL을 받으면 API를 다시 호출하여 새 URL 획득

### 2. 이미지가 없는 경우 처리

```java
// ✅ 올바른 방법: null 체크
String imageUrl = s3Service.generatePresignedUrlOrNull(book.getCoverImageKey());
if (imageUrl == null) {
    imageUrl = "https://default-image.com/no-image.jpg";  // 기본 이미지
}

// ❌ 잘못된 방법: null 체크 없이 사용
String imageUrl = s3Service.generatePresignedUrl(book.getCoverImageKey());  // NullPointerException 가능
```

### 3. S3 객체 키 형식 검증

```java
// 이미지 키 저장 시 형식 검증
public void validateImageKey(String imageKey) {
    if (imageKey == null || !imageKey.startsWith("images/")) {
        throw new BaseException(INVALID_IMAGE_KEY);
    }
}
```

### 4. 성능 고려사항

- PreSigned URL 생성은 **매우 빠름** (AWS SDK 내부에서 서명만 생성)
- 대량 조회 시에도 성능 이슈 없음
- 필요시 캐싱 가능하지만 1시간 만료를 고려해야 함

---

## 🔧 로컬 개발 환경 설정

### 1. 환경 변수 설정

`.env` 파일에 다음 추가:
```bash
AWS_ACCESS_KEY=your-access-key
AWS_SECRET_KEY=your-secret-key
AWS_S3_BUCKET=millie-collaboration-bucket
```

### 2. 프로필 활성화

`application.yml`에 이미 `s3` 프로필이 포함되어 있습니다:
```yaml
spring:
  profiles:
    include: s3
```

별도 설정 없이 애플리케이션 실행하면 자동으로 S3 기능 사용 가능합니다.

---

## 🧪 테스트

### S3 연결 테스트 API

애플리케이션 실행 후 다음 API로 S3 연결을 테스트할 수 있습니다:

```bash
# 1. S3 버킷 연결 확인
GET http://localhost:8080/api/test/s3/connection

# 2. S3 객체 목록 조회
GET http://localhost:8080/api/test/s3/list?prefix=images/book-covers

# 3. PreSigned URL 생성 테스트
GET http://localhost:8080/api/test/s3/presigned-url?objectKey=images/book-covers/BOOK_IMAGE_1.jpg
```

**⚠️ 주의**: `S3TestController`는 개발/테스트 용도이므로 운영 배포 전 제거 또는 `@Profile("local")` 추가 필요

---

## 📝 체크리스트

API 개발 시 아래 항목을 확인하세요:

- [ ] DB 엔티티에 `coverImageKey` 필드 추가 (String 타입)
- [ ] DB에는 S3 객체 키만 저장 (URL 저장 ❌)
- [ ] Service에 `S3Service` 주입
- [ ] API 응답 DTO에 `coverImageUrl` 필드 추가 (String 타입)
- [ ] `s3Service.generatePresignedUrlOrNull()` 사용하여 URL 생성
- [ ] null 처리 로직 추가 (이미지 없을 경우 기본 이미지 or null)
- [ ] Swagger에 응답 예시 추가

---

## 🆘 트러블슈팅

### 문제: `S3Service` Bean을 찾을 수 없음

**원인**: `s3` 프로필이 활성화되지 않음

**해결**:
```yaml
# application.yml 확인
spring:
  profiles:
    include: s3  # 이 부분이 있는지 확인
```

### 문제: PreSigned URL로 403 Forbidden 에러

**원인 1**: URL이 만료됨 (1시간 경과)
**해결**: API를 다시 호출하여 새 URL 획득

**원인 2**: S3 객체가 실제로 존재하지 않음
**해결**: S3 버킷에서 파일 존재 여부 확인

### 문제: 이미지가 깨져 보임

**원인**: 원본 이미지 해상도가 낮음
**해결**: 디자이너에게 @2x 해상도 (2배 크기) 이미지 요청

---

## 📚 참고 문서

- [AWS S3 설정 가이드](./AWS_S3_SETUP_GUIDE.md) - S3 버킷 생성 및 IAM 설정 방법
- [Spring Cloud AWS 공식 문서](https://docs.awspring.io/spring-cloud-aws/docs/3.1.0/reference/html/index.html)
- [AWS S3 PreSigned URL 개념](https://docs.aws.amazon.com/AmazonS3/latest/userguide/PresignedUrlUploadObject.html)

---

## 💬 문의

S3 관련 문제나 질문이 있으면 이슈 또는 슬랙으로 연락주세요!
