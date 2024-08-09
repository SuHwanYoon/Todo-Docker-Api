# 리액트 클라이언트 연동 REST API

이 프로젝트는 Spring Boot를 사용하여 RESTful API를 제공하는 서버 측 애플리케이션입니다. 
이 애플리케이션은 fly.io의 PostgreSQL 데이터베이스와 연동되며, 
인증 및 권한 관리를 포함한 기본적인 CRUD 기능을 제공합니다.


또한 Docker와 Github Action 을 사용해 CI/CD를 구현하였습니다.

## 프로젝트 개요

이 프로젝트는 다음과 같은 주요 기능을 제공합니다:
- RESTful API 제공
- 데이터베이스 연동 (PostgreQL)
- 사용자 인증 및 권한 관리
- CRUD 기능 (생성, 읽기, 업데이트, 삭제)

## 기술 스택

이 애플리케이션은 다음 기술과 라이브러리를 사용합니다:
- **Spring Boot**: 스프링 프레임워크 기반의 애플리케이션 프레임워크
- **Maven**: 프로젝트 빌드 및 의존성 관리를 위한 도구
- **PostgreSQL**: 관계형 데이터베이스 관리 시스템
- **Docker**: 애프리케이션 컨테이너화 및 배포, 일관성 유지를 위해 사용
- **Github Action**: 코드 푸시를 통해 Docker 이미지 빌드 배포 자동화

## ServerBaseURL
https://cdci-deploy-api.fly.dev/

API 엔드포인트
기본적으로 제공되는 API 엔드포인트는 다음과 같습니다:

- GET users/{username}/todos: 모든 작업 목록을 가져옵니다.
- GET users/{username}/todos/{id}: 특정 작업을 가져옵니다.
- POST users/{username}/todos: 새로운 작업을 생성합니다.
- POST /authenticate: JWT 토큰을 발급받습니다(로그인시).
- PUT users/{username}/todos/{id}: 특정 작업을 업데이트합니다.
- DELETE users/{username}/todos/{id}: 특정 작업을 삭제합니다.
