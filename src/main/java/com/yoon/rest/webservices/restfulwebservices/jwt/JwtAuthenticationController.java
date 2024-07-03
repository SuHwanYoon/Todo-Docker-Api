package com.yoon.rest.webservices.restfulwebservices.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {
    
    private final JwtTokenService tokenService;
    
    private final AuthenticationManager authenticationManager;

 // 생성자: JwtTokenService와 AuthenticationManager를 주입받음
    public JwtAuthenticationController(JwtTokenService tokenService, 
            AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }
    // '/authenticate' 엔드포인트에 대한 POST 요청을 처리
    @PostMapping("/authenticate")
    public ResponseEntity<JwtTokenResponse> generateToken(
            @RequestBody JwtTokenRequest jwtTokenRequest) {
    	// 사용자 이름과 비밀번호로 인증 토큰 생성
        var authenticationToken = 
                new UsernamePasswordAuthenticationToken(
                        jwtTokenRequest.username(), 
                        jwtTokenRequest.password());
     // 인증 매니저를 통해 인증 수행
        var authentication = 
                authenticationManager.authenticate(authenticationToken);
     // 인증된 정보를 바탕으로 JWT 토큰 생성
        var token = tokenService.generateToken(authentication);
        // 생성된 토큰을 응답으로 반환
        return ResponseEntity.ok(new JwtTokenResponse(token));
    }
}