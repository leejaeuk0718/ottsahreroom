# 1단계: 빌드 단계
FROM gradle:7.6-jdk17 AS build

WORKDIR /app

# 전체 프로젝트 복사
COPY . .

# JAR 빌드
RUN gradle clean bootJar --no-daemon


# 2단계: 실행 단계
FROM eclipse-temurin:17-jdk

WORKDIR /app

# 빌드된 jar 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 서버 포트
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
