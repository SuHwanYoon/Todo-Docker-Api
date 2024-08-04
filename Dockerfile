# 베이스 이미지로 OpenJDK 17을 사용합니다.
FROM openjdk:17-jdk-alpine

# 작업 디렉토리를 설정합니다.
WORKDIR /app

# 로컬에서 빌드된 JAR 파일을 Docker 이미지로 복사합니다.
COPY target/*.jar app.jar

# 로컬에서 빌드된 React 정적 파일을 Docker 이미지로 복사합니다.
# COPY src/main/resources/static /app/src/main/resources/static

# 컨테이너가 8080 포트를 사용함을 명시합니다.
EXPOSE 8080

# 애플리케이션을 실행합니다.
ENTRYPOINT ["java", "-jar", "app.jar"]
