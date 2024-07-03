package com.yoon.rest.webservices.restfulwebservices.jwt;

//JWT 토큰 요청을 위한 레코드 클래스
//username과 password를 포함
public record JwtTokenRequest(String username, String password) {}
