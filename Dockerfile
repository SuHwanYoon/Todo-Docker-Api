# 빌드 스테이지에서 Maven과 OpenJDK 17을 사용합니다.
FROM maven:3.8.5-openjdk-17 AS build

# 작업 디렉토리를 설정합니다.
WORKDIR /app

# 애플리케이션 소스 코드를 컨테이너의 작업 디렉토리로 복사합니다.
COPY . .

# Maven을 사용하여 애플리케이션을 빌드합니다. 테스트는 생략합니다.
RUN mvn clean package -DskipTests

# 런타임 스테이지에서 OpenJDK 17을 사용합니다.
FROM openjdk:17-jdk-alpine

# 작업 디렉토리를 설정합니다.
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일을 런타임 스테이지로 복사합니다.
COPY --from=build /app/target/*.jar app.jar

# 컨테이너가 8080 포트를 사용함을 명시합니다.
EXPOSE 8080

# 애플리케이션을 실행합니다.
ENTRYPOINT ["java", "-Dspring.profiles.active=deploy", "-jar", "app.jar"]
