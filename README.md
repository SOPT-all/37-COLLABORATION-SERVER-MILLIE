# 모바일 앱 6조 - 밀리의 서재

`Java - 17`

## 1. 프로젝트 구조

```
src/
├── main/
│   ├── java/
│   │   └── com.sopt.collaboration/
│   │       ├── domain/           # 도메인별 패키지
│   │       │   ├── member/
│   │       │   │   ├── controller/
│   │       │   │   ├── service/
│   │       │   │   ├── repository/
│   │       │   │   ├── dto/
│   │       │   │   ├── entity/
│   │       │   │   ├── exception/    # 도메인별 예외
│   │       │   │   │   ├── MemberException.java
│   │       │   │   │   └── MemberErrorCode.java
│   │       │   │   └── success/      # 도메인별 성공 코드
│   │       │   │       └── MemberSuccessCode.java
│   │       │   └── order/
│   │       │       ├── controller/
│   │       │       ├── service/
│   │       │       ├── repository/
│   │       │       ├── dto/
│   │       │       ├── entity/
│   │       │       ├── exception/
│   │       │       └── success/
│   │       ├── global/           # 공통 설정
│   │       └── Application.java
│   └── resources/
└── test/
```

## 2. 네이밍 컨벤션

**클래스명**

- Controller: `UserController`, `OrderController`
- Service: `UserService`, `OrderService`
- Repository: `UserRepository`, `OrderRepository`
- DTO: `UserRequestDto`, `UserResponseDto`
- Entity: `User`, `Order` (접미사 없이)

**메서드명**

- 조회: `getUser()`, `findUserById()`, `findUserList()`

  `getUser(Long id)` // get: 없으면 예외처리

  `findUser(Long id)` // find: 없을 수도 있을 때(Optional 반환)

- 생성: `createUser()`, `saveUser()`
- 수정: `updateUser()`, `modifyUser()`
- 삭제: `deleteUser()`, `removeUser()`
- 검증: `validateUser()`, `checkUserExists()`

**변수명**

- 카멜케이스 사용: `userId`, `userName`
- boolean 타입: `isActive`, `hasPermission`
- Collection: 복수형 사용: `users`

## 3. 레이어별 책임

**Controller**

- HTTP 요청/응답 처리
- 입력 검증 (`@Valid`)
- 비즈니스 로직 포함 금지

```java
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // 데이터가 있는 성공 응답
  @GetMapping("/{id}")
  public CommonApiResponse<UserResponseDto> getUser(@PathVariable Long id) {
    UserResponseDto user = userService.getUser(id);
    return CommonApiResponse.success(SuccessCode.SUCCESS, user);
  }

  // 데이터가 없는 성공 응답 (생성, 삭제 등)
  @PostMapping
  public CommonApiResponse<Void> createUser(@Valid @RequestBody UserRequestDto request) {
    userService.createUser(request);
    return CommonApiResponse.success(SuccessCode.SUCCESS);
  }
}
```

**Service**

- 비즈니스 로직 처리
- 트랜잭션 관리 (`@Transactional`)
- 여러 Repository 조합

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserRequestDto request) {
        // 1. Request DTO → Entity 변환
        User user = request.toEntity();
        
        // 2. Entity 저장
        User savedUser = userRepository.save(user);
        
        // 3. Entity → Response DTO 변환
        return UserResponseDto.from(savedUser);
    }
}
```

**Repository**

- 데이터 접근만 담당
- JPA 메서드 네이밍 규칙 준수
- 복잡한 쿼리는 `@Query` 또는 QueryDSL 사용

## 4. DTO 작성 규칙

```java
// Request DTO
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {

    @NotBlank(message = "이름은 필수입니다")
    private String name;

    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    // DTO → Entity 변환
    public User toEntity() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .build();
    }
}

// Response DTO
@Getter
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;

    // Entity → DTO 변환
    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
```

## 5. Entity 작성 규칙

```java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Builder
    private User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // 비즈니스 로직은 Entity 내부에 작성
    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }
}
```

**Entity 규칙**

- `@Setter` 사용 금지 (불변성 보장)
- `@Getter` 는 쓸 때 한번 고민해보기!
- 생성자는 `@Builder` 사용
- 비즈니스 로직은 Entity 내부에 작성

## 6. Configuration 작성

```java
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 설정 내용
        return http.build();
    }
}
```

- `@Configuration` 클래스는 `global/config` 패키지에 위치
- 각 설정은 목적별로 분리 (Security, Database, Redis 등)

## 7. Git Commit 컨벤션

| 커밋 유형      | 의미 |
|------------| --- |
| `Feat`     | 새로운 기능 추가 |
| `Fix`      | 버그 수정 |
| `Docs`     | 문서 수정 |
| `Style`    | 코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우 |
| `Refactor` | 코드 리팩토링 |
| `Test`     | 테스트 코드, 리팩토링 테스트 코드 추가 |
| `Chore`    | 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore |
| `Rename`   | 파일 또는 폴더 명을 수정하거나 옮기는 작업만인 경우 |
| `Remove`   | 파일을 삭제하는 작업만 수행한 경우 |
| `!HOTFIX`  | 급하게 치명적인 버그를 고쳐야 하는 경우 |

```
[Feat] <domain>: 새로운 기능 추가
[Fix] <domain>: 버그 수정
[Refactor] <domain>: 코드 리팩토링
[Style] <domain>: 코드 포맷팅, 세미콜론 누락 등
[Docs]: 문서 수정
[Test]: 테스트 코드 추가/수정
[Chore]: 빌드 업무, 패키지 매니저 수정
```

**예시**: [Feat] User: 로그인 기능 추가

## 8. Git Branch 컨벤션

```smalltalk
<이슈번호>-<커밋 유형>/내용

Git Flow

main(배포 버전 코드)
dev(개발 단계 코드)

// 영어로 쓰기
15-feature/implement-user-login
```

## 9. Issue 잘 만들기!

```java
템플릿에 맞춰

버그 찾으면 버그 이슈화
기능 추가 티켓 다 이슈화
```

## 10. 환경 설정 관리

```yaml
# application.yml
spring:
  profiles:
    active: ${PROFILE:dev}

# application-dev.yml (개발)
# application-prod.yml (운영)
```

- 민감 정보는 환경 변수로 관리
- `.env` 파일은 `.gitignore`에 추가

## 11. 공통 응답 (API Response) 컨벤션

**모든 API는 `CommonApiResponse`로 통일된 응답 형식을 사용합니다.**

### 응답 구조

```json
{
  "code": "S200",
  "message": "성공",
  "data": { ... }
}
```

### Success Code 추가 방법

**도메인별로 SuccessCode를 분리하여 관리합니다.**

1. **공통 성공 코드** (`SuccessCode.java`)

```java
@Getter
public enum SuccessCode implements SuccessType {
    SUCCESS("S200", "성공");

    private final String code;
    private final String message;

    SuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
```

2. **도메인별 성공 코드** (예: `MemberSuccessCode.java`)

```java
@Getter
public enum MemberSuccessCode implements SuccessType {
    MEMBER_CREATED("M001", "회원 가입 성공"),
    MEMBER_RETRIEVED("M002", "회원 정보 조회 성공"),
    MEMBER_UPDATED("M003", "회원 정보 수정 성공"),
    MEMBER_DELETED("M004", "회원 탈퇴 성공");

    private final String code;
    private final String message;

    MemberSuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
```

3. **Controller에서 사용**

```java
@GetMapping("/{id}")
public CommonApiResponse<UserResponseDto> getUser(@PathVariable Long id) {
    UserResponseDto user = userService.getUser(id);
    return CommonApiResponse.success(MemberSuccessCode.MEMBER_RETRIEVED, user);
}
```

**코드 네이밍 규칙**
- `S###`: 공통 성공 코드
- `M###`: Member(회원) 관련
- `O###`: Order(주문) 관련
- `B###`: Book(도서) 관련
- 각 도메인별로 001부터 099까지 할당

## 12. 예외 처리 컨벤션

**도메인별로 ErrorCode와 Exception을 분리하여 관리합니다.**

### 예외 처리 구조

```
GlobalExceptionHandler
    ├── BaseException (커스텀 비즈니스 예외)
    │   ├── MemberException extends BaseException
    │   ├── OrderException extends BaseException
    │   └── BookException extends BaseException
    ├── MethodArgumentNotValidException (@Valid 검증 실패)
    ├── IllegalArgumentException (도메인 검증 실패)
    ├── HttpMessageNotReadableException (JSON 파싱 실패)
    └── Exception (그 외 모든 예외)
```

### Error Code 추가 방법

1. **공통 에러 코드** (`ErrorCode.java`)

```java
@Getter
public enum ErrorCode implements ErrorType {
    // 공통 에러 (C001~C099)
    INVALID_INPUT("C001", "입력값이 올바르지 않습니다", 400),
    INVALID_FORMAT("C002", "데이터 형식이 올바르지 않습니다", 400),
    INTERNAL_SERVER_ERROR("C999", "서버 내부 오류가 발생했습니다", 500);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
```

2. **도메인별 에러 코드** (예: `MemberErrorCode.java`)

```java
@Getter
public enum MemberErrorCode implements ErrorType {
    USER_NOT_FOUND("M001", "사용자를 찾을 수 없습니다", 404),
    DUPLICATE_EMAIL("M002", "이미 존재하는 이메일입니다", 409),
    INVALID_PASSWORD("M003", "비밀번호가 일치하지 않습니다", 401);

    private final String code;
    private final String message;
    private final int status;

    MemberErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
```

3. **도메인별 Exception** (예: `MemberException.java`)

```java
public class MemberException extends BaseException {
    public MemberException(ErrorType errorType) {
        super(errorType);
    }

    public MemberException(ErrorType errorType, String detail) {
        super(errorType, detail);
    }
}
```

### Service에서 예외 발생

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getUser(Long id) {
        // 도메인별 Exception과 ErrorCode 사용
        User user = userRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberErrorCode.USER_NOT_FOUND));

        return UserResponseDto.from(user);
    }

    public void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            // 커스텀 메시지와 함께 예외 발생
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL, email);
        }
    }
}
```

**코드 네이밍 규칙**
- `C###`: 공통 에러 코드
- `M###`: Member(회원) 관련
- `O###`: Order(주문) 관련
- `B###`: Book(도서) 관련
- 각 도메인별로 001부터 099까지 할당

**파일 위치**
```
domain/
├── member/
│   ├── exception/
│   │   ├── MemberException.java
│   │   └── MemberErrorCode.java
│   └── success/
│       └── MemberSuccessCode.java
└── order/
    ├── exception/
    │   ├── OrderException.java
    │   └── OrderErrorCode.java
    └── success/
        └── OrderSuccessCode.java
```

### 에러 응답 형식

```json
{
  "code": "M001",
  "message": "사용자를 찾을 수 없습니다",
  "data": null
}
```

**Validation 실패 시**
```json
{
  "code": "C001",
  "message": "입력값이 올바르지 않습니다",
  "data": {
    "email": "올바른 이메일 형식이 아닙니다",
    "name": "이름은 필수입니다"
  }
}
```

## 13. Swagger 컨벤션

**Swagger를 통해 자동으로 API 문서가 생성됩니다.**

### Swagger 접속

- 개발 환경: `http://localhost:8080/swagger-ui.html`
- 배포 환경: `${SWAGGER_BASE_URL}/swagger-ui.html`

### Controller에 Swagger 어노테이션 추가

```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "회원 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 정보 조회", description = "ID로 회원 정보를 조회합니다")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음",
                content = @Content(schema = @Schema(implementation = CommonApiResponse.class)))
    })
    @GetMapping("/{id}")
    public CommonApiResponse<UserResponseDto> getUser(
            @Parameter(description = "회원 ID", required = true) @PathVariable Long id) {
        UserResponseDto user = userService.getUser(id);
        return CommonApiResponse.success(SuccessCode.SUCCESS, user);
    }

    @Operation(summary = "회원 가입", description = "새로운 회원을 생성합니다")
    @PostMapping
    public CommonApiResponse<Void> createUser(
            @Valid @RequestBody UserRequestDto request) {
        userService.createUser(request);
        return CommonApiResponse.success(SuccessCode.SUCCESS);
    }
}
```

### DTO에 Swagger 어노테이션 추가

```java
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "회원 생성 요청")
public class UserRequestDto {

    @Schema(description = "회원 이름", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이름은 필수입니다")
    private String name;

    @Schema(description = "이메일 주소", example = "hong@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;
}

@Getter
@Builder
@Schema(description = "회원 정보 응답")
public class UserResponseDto {

    @Schema(description = "회원 ID", example = "1")
    private Long id;

    @Schema(description = "회원 이름", example = "홍길동")
    private String name;

    @Schema(description = "이메일 주소", example = "hong@example.com")
    private String email;

    @Schema(description = "생성일시", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
}
```

### Swagger 어노테이션 정리

| 어노테이션 | 사용 위치 | 설명 |
|-----------|----------|------|
| `@Tag` | Controller 클래스 | API 그룹 정의 |
| `@Operation` | Controller 메서드 | API 설명 |
| `@ApiResponses` | Controller 메서드 | 응답 코드별 설명 |
| `@Parameter` | 메서드 파라미터 | 파라미터 설명 |
| `@Schema` | DTO 클래스/필드 | 스키마 설명 |
