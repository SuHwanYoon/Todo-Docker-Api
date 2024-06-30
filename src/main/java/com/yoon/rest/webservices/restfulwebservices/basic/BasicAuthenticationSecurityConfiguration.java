package com.yoon.rest.webservices.restfulwebservices.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

//SpringSecurity 설정 클래스
//@Configuration은 하나이상의 @Bean메서드를 선언한다는 뜻
@Configuration
public class BasicAuthenticationSecurityConfiguration {

	// SpringSecurity는 모든 http요청에 대해서 인증을 요구한다

	// 사용자 정의 SecurityFilterChain을 반환하는 Bean method
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 1. 모든 http요청을 인증받도록 설정
		// 기본 로그인 web view 대신 오류페이지(403)가 나온다
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		// 2. http 요청이 들어오면 인증을 위한 브라우저 기본 인증 팝업창(JS의 prompt형식)을 보여준다
		http.httpBasic(Customizer.withDefaults());

		// 3. Stateless session 설정
		http.sessionManagement(
				session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);
		//csrf 비활성화
		http.csrf().disable();
		//return 할 http 상태 최종 빌드
		return http.build();
		
		
		// 메소드 체이닝 버전 - http 중복 참조을 없애고 세미콜론 제거 최종 build형태 return
//      return 
//              http
//                  .authorizeHttpRequests(
//                      auth -> 
//                          auth
//                          .anyRequest().authenticated()
//                      )
//                  .httpBasic(Customizer.withDefaults())
//                  .sessionManagement(
//                      session -> session.sessionCreationPolicy
//                      (SessionCreationPolicy.STATELESS))
//                  .csrf().disable()
//                  .build();
	}

}
