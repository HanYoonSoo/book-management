# book-management-system

## 저자 & 도서 삭제 관련
저자 삭제 시 Soft Delete로 삭제됩니다.<br> 
해당 저자의 도서 또한 Soft Delete로 DB에서 관리됩니다.<br>
삭제 시 저자 email: deleted\_(UUID)\_email으로 초기화 됩니다.<br>
삭제 시 도서 isbn: deleted\_(UUID)\_isbn으로 초기화 됩니다.<br>

설계 이유:
1. Soft Delete -> 저자, 도서 삭제 시 데이터 보존.
2. 삭제 초기화 형식 지정 -> 삭제 된 저자 email, 도서 isbn의 재생성 가능.
3. UUID 사용 -> UUID 미사용 시 한번 삭제 된 데이터와 동일한 데이터 삭제 시 UNIQUE 충돌
   - 예) abc@example.com 이메일을 가진 Author를 2번 삭제하는 경우(deleted_abc@example.com)

## 설치 및 실행 방법
윈도우 작업 시 Git Bash를 사용해서 작업해주세요. UNIX 기반 운영체제의 경우 Terminal를 사용해서 작업해주세요.
1. 폴더 생성(윈도우), 디렉토리 생성(UNIX 기반)
2. git clone https://github.com/HanYoonSoo/book-management.git 명령어 수행
3. cd book-management
4. chmod u+x gradlew
5. ./gradlew bootRun

## API 사용 예시 작성

Postman API 사용 예시 경로: https://documenter.getpostman.com/view/40121693/2sAYdhHpg2

![예시 선택 사진](images/select_example.png)

## Swagger 문서 접근 방법

실행 방법 대로 실행 시킨 이후, 아래 경로로 접속<br>
Swagger 접속 경로: http://localhost:8080/swagger-ui/index.html#
 
## 기타 주의 사항
애플리케이션 포트(8080)이 이미 실행중인 경우 프로젝트가 정상적으로 실행되지 않을 수 있습니다.<br>
git이 설치되어 있는 환경이어야 합니다.<br>
Java가 설치되어 있는 환경이어야 합니다.<br>

## 개발 환경
- Java 17
- Spring Boot 3.4.3
- Gradle 8.12.1
- JPA
- QueryDSL
- H2
- Flyway
