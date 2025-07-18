# 스터디 관리 시스템 (Study Management System)

Spring Boot, Spring Security, MyBatis, MySQL을 사용하여 제작한 스터디 관리 웹 애플리케이션입니다.

## 프로젝트 개요

회원가입 후 스터디를 개설하거나 참여 신청할 수 있는 웹 서비스입니다. 로그인한 사용자만 스터디 개설 및 참여가 가능하며, 각 스터디는 최대 참여 인원이 제한됩니다.

## 주요 기능

* **회원 관리**: 회원가입, 로그인/로그아웃 (Spring Security 기반)
* **스터디 관리**: 스터디 생성, 수정, 삭제
* **스터디 참여 신청**: 최대 인원 제한 적용, 중복 신청 불가
* **검색 및 필터링**: 제목 및 작성자 이름으로 스터디 검색
* **마이페이지**: 내가 개설한 스터디 및 신청한 스터디 목록 확인
* **반응형 디자인**: 모바일 대응

## 사용 기술

* **백엔드**: Spring Boot 3.1.5, MyBatis 3.x, Spring Security 6.x (세션 기반 인증)
* **데이터베이스**: MySQL 8.0
* **프론트엔드**: Thymeleaf, HTML5, CSS3, JavaScript
* **빌드 도구**: Gradle

## 설치 및 실행 방법

### 1. 프로젝트 클론

```bash
git clone <repository-url>
cd study-management-system
```

### 2. 데이터베이스 설정

* MySQL에서 `study_management` 데이터베이스 생성:

```sql
CREATE DATABASE study_management;
```

* `database.sql` 파일을 구현하여 테이블 생성 및 초기 데이터 입력:

```bash
mysql -u root -p study_management < database.sql
```

### 3. 애플리케이션 설정 (`application.properties`)

`src/main/resources/application.properties` 파일에 개인 DB 접속 정보를 설정하세요.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/study_management?serverTimezone=Asia/Seoul
spring.datasource.username=사용자명
spring.datasource.password=비밀번호

mybatis.mapper-locations=classpath:mappers/*.xml
mybatis.type-aliases-package=com.study.management.entity
mybatis.configuration.map-underscore-to-camel-case=true

spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML
spring.thymeleaf.cache=false

logging.level.com.study.management=DEBUG
```

### 4. 빌드 및 실행

```bash
./gradlew build
./gradlew bootRun
```

웹 애플리케이션은 `http://localhost:8080`에서 확인 가능합니다.

## 기본 제공 계정

모든 기본 계정 비밀번호는 `password` 입니다.

* 관리자: `admin@example.com`
* 일반 사용자1: `john@example.com`
* 일반 사용자2: `jane@example.com`

## API 엔드포인트

### 공개 (모두 접근 가능)

* 홈 페이지 (`/`)
* 로그인 (`/login`)
* 회원가입 (`/register`)
* 스터디 목록 보기 (`/studies/list`)
* 스터디 상세 보기 (`/studies/detail/{id}`)

### 보호 (로그인 필요)

* 마이페이지 (`/mypage`)
* 스터디 개설 (`/studies/create`)
* 스터디 수정 (`/studies/edit/{id}`)
* 스터디 삭제 (`/studies/delete/{id}`)
* 스터디 신청 (`/studies/apply/{id}`)
* 로그아웃 (`/logout`)

## 데이터베이스 스키마

### 회원 테이블 (`users`)

* user\_id (PK)
* username (고유값)
* email (고유값)
* password (BCrypt 암호화)
* full\_name
* created\_at
* updated\_at

### 스터디 테이블 (`studies`)

* study\_id (PK)
* title
* description
* max\_participants
* current\_participants
* deadline
* status
* creator\_id (users 외래키)
* created\_at
* updated\_at

### 스터디 신청 테이블 (`study_applications`)

* application\_id (PK)
* study\_id (studies 외래키)
* user\_id (users 외래키)
* status
* applied\_at

## 보안 기능

* 비밀번호 BCrypt 암호화
* 세션 기반 로그인 관리
* CSRF 보호 (Spring Security 기본)
* 역할 기반 접근 권한
* SQL Injection 방지 (MyBatis의 Prepared Statement 사용)

## 사용법

1. **회원가입 및 로그인**
2. **스터디 검색 및 확인**
3. **스터디 개설 및 수정**
4. **스터디 신청 및 관리**

## 프로젝트 구조

```
src/
├── main/
│   ├── java/com/study/management/
│   │   ├── config/          # 설정 클래스
│   │   ├── controller/      # 컨트롤러 클래스
│   │   ├── entity/          # 엔티티 클래스
│   │   ├── mapper/          # MyBatis 인터페이스
│   │   └── service/         # 비즈니스 로직
│   └── resources/
│       ├── mappers/         # MyBatis XML 매퍼
│       ├── static/          # 정적 리소스
│       └── templates/       # Thymeleaf 템플릿
└── test/                    # 테스트 코드
```

