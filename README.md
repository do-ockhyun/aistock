# StockGPT - 증권 AI 비서

Spring Boot와 Spring AI를 기반으로 제작된 대화형 증권 정보 AI 서비스입니다. 사용자의 자연어 질문을 이해하고, 실시간 주가, 시장 지수 등 다양한 증권 정보를 제공합니다.

## ✨ 주요 기능

- **🤖 자연어 처리 및 의도 분석**: 사용자의 질문(예: "오늘 삼성전자 주가 알려줘")을 AI가 분석하여 핵심 의도(주가 조회)와 개체(삼성전자)를 추출합니다.
- **💬 동적 응답 생성**: 분석된 의도를 바탕으로, 각 상황에 맞는 자연스러운 텍스트 응답을 동적으로 생성합니다.
- **🎨 시각적 강조**: 주가 및 지수의 상승/하락을 **<span style="color:red">붉은색</span>**과 **<span style="color:blue">파란색</span>**으로 시각적으로 구분하여 가독성을 높였습니다.
- **📱 반응형 채팅 UI**: 데스크톱과 모바일 환경 모두에 최적화된 깔끔한 채팅 인터페이스를 제공합니다.
- **🔗 동적 바로가기**: 응답 내용에 따라 관련 기능으로 바로 이동할 수 있는 바로가기 링크를 제공하여 사용자 편의성을 높였습니다.

## 🛠️ 사용 기술

- **Backend**: `Java 17`, `Spring Boot 3`, `Spring AI`
- **Frontend**: `HTML`, `CSS`, `JavaScript`
- **AI**: `OpenAI GPT`
- **Build**: `Maven`

## 🚀 실행 방법

1.  **Repository 클론:**
    ```bash
    git clone https://github.com/misolab/aistock.git
    cd aistock
    ```

2.  **API 키 설정:**
    `src/main/resources/application.yml` 파일에 OpenAI API 키를 설정하거나, 시스템 환경 변수 `SPRING_AI_OPENAI_API_KEY`를 설정합니다.

    ```yaml
    spring:
      ai:
        openai:
          api-key: YOUR_API_KEY # 또는 ${SPRING_AI_OPENAI_API_KEY}
    ```

3.  **애플리케이션 실행:**
    ```bash
    ./mvnw spring-boot:run
    ```

4.  **서비스 접속:**
    웹 브라우저에서 `http://localhost:8080`으로 접속합니다.

## 📝 API Endpoints

- `POST /api/v1/chat/query`: 사용자의 채팅 메시지를 받아 AI 처리 후 응답을 반환합니다.
- `GET /api/v1/intents`: 현재 서비스가 지원하는 기능(의도) 목록을 조회합니다.
