# 베이스 이미지로 OpenJDK 17을 사용합니다.
FROM openjdk:17-jdk-alpine

# 작업 디렉토리를 설정합니다.
WORKDIR /app

# 소스 파일 전체를 복사합니다.
COPY . /app

# 빌드를 진행합니다. 테스트는 생략합니다.
RUN ./mvnw clean package -DskipTests

# 빌드된 JAR 파일을 Docker 이미지로 복사합니다.
COPY target/*.jar app.jar

# 컨테이너가 8080 포트를 사용함을 명시합니다.
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-Dspring.profiles.active=deploy", "-jar", "app.jar"]
