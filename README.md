# 모바일 앱 6조 - 밀리의 서재

`Java - 17`

## 1. 프로젝트 구조

```
src/
├── main/
│   ├── java/
│   │   └── com.company.project/
│   │       ├── domain/           # 도메인별 패키지
│   │       │   ├── user/
│   │       │   │   ├── controller/
│   │       │   │   ├── service/
│   │       │   │   ├── repository/
│   │       │   │   ├── dto/
│   │       │   │   └── entity/
│   │       │   └── order/
│   │       ├── global/           # 공통 설정
│   │       │   ├── config/
│   │       │   ├── exception/
│   │       │   └── util/
│   │       └── Application.java
│   └── resources/
│       ├── application.yml
│       └── application-prod.yml
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
- Collection: 복수형 사용: `users`, `orderList`

## 3. 레이어별 책임

**Controller**

- HTTP 요청/응답 처리
- 입력 검증 (`@Valid`)
- 비즈니스 로직 포함 금지

```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.createUser(request));
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

| 커밋 유형 | 의미 |
| --- | --- |
| `feat` | 새로운 기능 추가 |
| `fix` | 버그 수정 |
| `docs` | 문서 수정 |
| `style` | 코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우 |
| `refactor` | 코드 리팩토링 |
| `test` | 테스트 코드, 리팩토링 테스트 코드 추가 |
| `chore` | 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore |
| `Rename` | 파일 또는 폴더 명을 수정하거나 옮기는 작업만인 경우 |
| `Remove` | 파일을 삭제하는 작업만 수행한 경우 |
| `!HOTFIX` | 급하게 치명적인 버그를 고쳐야 하는 경우 |

```
[Feat] <domain>: 새로운 기능 추가
[fix] <domain>: 버그 수정
[Refactor] <domain>: 코드 리팩토링
[Style] <domain>: 코드 포맷팅, 세미콜론 누락 등
[docs]: 문서 수정
[test]: 테스트 코드 추가/수정
[chore]: 빌드 업무, 패키지 매니저 수정
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
