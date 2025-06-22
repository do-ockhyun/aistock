# 1. Build Stage: Maven과 JDK를 사용하여 애플리케이션을 빌드합니다.
FROM maven:3.8.5-openjdk-17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# pom.xml 파일을 먼저 복사하여 종속성을 캐싱합니다.
COPY pom.xml .
RUN mvn dependency:go-offline

# 소스 코드를 복사합니다.
COPY src ./src

# Maven을 사용하여 애플리케이션을 패키징합니다. (테스트는 생략하여 빌드 속도 향상)
RUN mvn package -DskipTests


# 2. Run Stage: 빌드된 JAR 파일을 실행합니다.
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# Build stage에서 생성된 JAR 파일을 복사합니다.
COPY --from=build /app/target/*.jar app.jar

# 애플리케이션 포트를 8080으로 노출합니다. Render는 이 포트를 사용합니다.
EXPOSE 8080

# 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"] 