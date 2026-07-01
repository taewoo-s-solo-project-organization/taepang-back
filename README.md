# Taepang

> 온라인 ECommerce (이커머스) 플랫폼

Spring Boot 기반의 이커머스 프로젝트 태팡의 API 서버입니다.

---

## Tech Stack

| 구분        | 기술                          |
|-----------|-----------------------------|
| Language  | Java 21                     |
| Framework | Spring Boot 3.5.x           |
| ORM       | Spring Data JPA / Hibernate |
| Database  | MySQL                       |
| Build     | Gradle                      |
| 기타        | Lombok                      |

---

## Getting Started

### Prerequisites

- JDK 21
- MySQL 8.x
- Gradle (Wrapper 포함)

### Database

로컬 MySQL에 데이터베이스를 생성합니다.

```sql
CREATE
DATABASE local_taepang CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Configuration

`src/main/resources/application.properties` 또는 환경 변수로 DB 접속 정보를 설정합니다.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/local_taepang
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

> 운영 환경에서는 비밀번호를 코드/저장소에 직접 넣지 말고 환경 변수로 관리하는 것을 권장합니다.

### Run

```bash
# Windows
gradlew.bat bootRun

# macOS / Linux
./gradlew bootRun
```

기본 포트: `8080`

### Test

```bash
gradlew.bat test
```

---

## Project Structure (추가 및 수정 예정)

```
src/main/java/com/example/taepang/
├── TaepangApplication.java
└── domain/
    └── member/
        ├── controller/   # REST API
        ├── service/      # 비즈니스 로직
        ├── repository/   # JPA Repository
        ├── entity/       # JPA Entity
        └── dto/          # Request / Response DTO
```

---

## Domain Model (Member)

| Entity    | 설명    | 비고                              |
|-----------|-------|---------------------------------|
| `User`    | 일반 회원 | `email` unique                  |
| `Seller`  | 판매자   | `User`와 1:1 (`sellers.user_id`) |
| `Manager` | 관리자   | TODO: 인증/암호화 정책 정의 필요           |

### User ↔ Seller 관계

- `User`는 `Seller` 없이 존재할 수 있음 (일반 회원)
- `Seller`는 반드시 `User`를 참조해야 함
- FK는 `Seller` 쪽(`user_id`)에 위치

---

## API (User)

Base URL: `/user`

| Method   | Path           | 설명       | Status           |
|----------|----------------|----------|------------------|
| `POST`   | `/join`        | 회원 가입    | `201 Created`    |
| `GET`    | `/info/{id}`   | 회원 조회    | `200 OK`         |
| `PATCH`  | `/modify/{id}` | 회원 정보 수정 | `200 OK`         |
| `DELETE` | `/remove/{id}` | 회원 삭제    | `204 No Content` |

### POST `/user/join`

**Request**

```json
{
  "username": "홍길동",
  "phoneNumber": "010-1234-5678",
  "email": "user@example.com"
}
```

**Response (`201`)**

```json
{
  "username": "홍길동",
  "phoneNumber": "010-1234-5678",
  "email": "user@example.com",
  "createdAt": "2026-06-28T12:00:00",
  "updatedAt": "2026-06-28T12:00:00"
}
```

**Error**

- 이메일 중복: `409 Conflict` (TODO: 전역 예외 처리 적용 후 명시)

### PATCH `/user/modify/{id}`

null이 아닌 필드만 수정합니다.

```json
{
  "username": "새이름",
  "email": "new@example.com",
  "phoneNumber": "010-9999-8888"
}
```

&nbsp;

> 원하는 필드 (username, email, phoneNumber) 만 작성하기도 가능

```json
{
  "username": "새이름",
  "email": "new@example.com"
}
```

---

## TODO / Roadmap (추가 예정)

- [ ] Seller / Manager API 구현
- [ ] 전역 예외 처리 (`@RestControllerAdvice`)
- [ ] 회원 삭제 Soft Delete 전환
- [ ] DTO 중복 정리 (`CreateUserResDto` / `FindUserResDto` 등)
- [ ] 요청값 검증 (`@Valid`, `@NotBlank`, `@Email`)
- [ ] 인증/인가 (Spring Security, JWT 등)
- [ ] API 문서 (Swagger / SpringDoc)
- [ ] CI / CD 구축

---
