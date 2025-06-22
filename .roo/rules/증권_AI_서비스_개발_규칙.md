---
description: 
globs: 
alwaysApply: false
---
# 증권 AI 서비스 개발 규칙

## 프로젝트 개요
- Spring Boot 3.5.3 기반 증권 AI 질의응답 서비스 MVP
- 자연어 질의를 파싱하여 구조화된 쿼리로 변환 후 주가 API 호출
- 모놀리식 아키텍처로 빠른 프로토타이핑 중심

## 코딩 스타일 및 규칙

### Java/Spring Boot
- **패키지 구조**: 도메인별 분리 (stock, market, user, ai)
- **네이밍**: 
  - 클래스: PascalCase (StockPriceService)
  - 메소드/변수: camelCase (getCurrentPrice)
  - 상수: UPPER_SNAKE_CASE (MAX_RETRY_COUNT)
- **어노테이션**: 
  - @RestController, @Service, @Repository 명시적 사용
  - @Validated, @Valid 를 통한 입력값 검증
- **예외 처리**: 
  - 커스텀 예외 클래스 생성 (StockNotFoundException)
  - @ControllerAdvice로 전역 예외 처리

### API 설계
- **RESTful 원칙** 준수
- **응답 형식**: ResponseEntity<ApiResponse<T>> 통일
- **HTTP 상태코드**: 명확한 의미 전달
- **API 버저닝**: /api/v1/ prefix 사용

### AI/LLM 연동
- **프롬프트**: 별도 properties/yaml 파일로 관리
- **파싱 결과**: JSON 구조화된 응답 강제
- **Fallback**: AI 파싱 실패시 기본 응답 제공
- **캐싱**: Redis 활용한 반복 질의 최적화

### 데이터베이스
- **JPA**: Entity 설계시 연관관계 명시
- **쿼리**: 복잡한 쿼리는 @Query 또는 QueryDSL 활용
- **트랜잭션**: @Transactional 적절한 범위 설정

## 보안 및 컴플라이언스

### 증권업 특화 규칙
- **투자권유 금지**: 응답에 "투자 참고용" 문구 필수 포함
- **면책조항**: 모든 주가 정보에 지연 정보임을 명시
- **개인정보**: 사용자 포트폴리오 정보 암호화 저장

### 입력값 검증
- **종목코드**: 6자리 숫자 형식 검증
- **날짜**: yyyy-MM-dd 형식 강제
- **SQL Injection**: PreparedStatement 사용 강제

## 성능 최적화

### 캐싱 전략
- **주가 정보**: 실시간 데이터는 30초, 과거 데이터는 1일 캐싱
- **AI 파싱 결과**: 동일 질의는 1시간 캐싱
- **종목 마스터**: 1일 캐싱

### 비동기 처리
- **외부 API 호출**: @Async 활용
- **배치 처리**: @Scheduled 로 정기 데이터 갱신

## 테스트 규칙

### 단위 테스트
- **Service 계층**: Mockito 활용한 단위 테스트 필수
- **Controller**: MockMvc 를 통한 API 테스트
- **커버리지**: 최소 70% 이상 유지

### 통합 테스트
- **@SpringBootTest**: 전체 플로우 검증
- **TestContainer**: 외부 의존성 격리

## 로깅 및 모니터링

### 로그 레벨
- **DEBUG**: 개발시 상세 플로우 추적
- **INFO**: 비즈니스 로직 실행 기록  
- **WARN**: 비정상적이지만 처리 가능한 상황
- **ERROR**: 시스템 오류 및 예외 상황

### 모니터링 포인트
- **AI API 응답시간**: 3초 초과시 알림
- **주가 API 실패율**: 5% 초과시 알림
- **메모리 사용량**: 80% 초과시 알림

## MVP 단계별 우선순위

### Phase 1 
- 기본 Intent 정의 (stock_price_inquiry, market_index_analysis)
- AI 파싱 프롬프트 구현
- Mock API 우선 개발

### Phase 2
- 실제 주가 시스템 연동
- 개인화 서비스 (watchlist_ranking, portfolio_analysis)
- 기본 UI 구현

### Phase 3 (향후 확장)
- 고급 분석 기능
- 성능 최적화
- 상용 서비스 전환

## 코드 리뷰 체크리스트

### 필수 확인사항
- [ ] 컴플라이언스 문구 포함 여부
- [ ] 예외 처리 구현 여부  
- [ ] 입력값 검증 구현 여부
- [ ] 테스트 코드 작성 여부
- [ ] API 문서 업데이트 여부

### 성능 확인사항
- [ ] N+1 쿼리 문제 없는지
- [ ] 캐싱 적용 가능한지
- [ ] 메모리 누수 가능성 없는지

## 개발 도구 및 플러그인

### 필수 플러그인
- **Spring Boot Extension Pack**: Spring 개발 지원
- **Java Extension Pack**: Java 개발 환경
- **REST Client**: API 테스트용
- **Database Client**: DB 연동 및 쿼리 테스트

### 코드 품질
- **SonarLint**: 정적 분석
- **Checkstyle**: 코딩 컨벤션 체크
- **SpotBugs**: 버그 패턴 탐지

## 환경별 설정

### 개발환경 (application-dev.yml)
- H2 인메모리 DB 사용
- AI API Mock 서버 연동
- 로그 레벨: DEBUG

### 운영환경 (application-prod.yml)  
- PostgreSQL/MySQL 사용
- 실제 AI API 연동
- 로그 레벨: INFO
- 보안 설정 강화

이 규칙들을 따라 개발하되, MVP 특성상 빠른 검증이 우선이므로 완벽함보다는 동작하는 프로토타입을 먼저 만드는 것을 권장합니다.
