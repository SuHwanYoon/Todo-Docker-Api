package com.yoon.rest.webservices.restfulwebservices.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

//SpringSecurity 설정 클래스
//@Configuration은 하나이상의 @Bean메서드를 선언한다는 뜻
//JWT사용을 위해 기본인증은 주석처리
//@Configuration
public class BasicAuthenticationSecurityConfiguration {

	// SpringSecurity는 모든 http요청에 대해서 인증을 요구한다

	// 사용자 정의 SecurityFilterChain을 반환하는 Bean method
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 1. 모든 http요청을 인증받도록 설정
		// 기본 로그인 web view 대신 오류페이지(403)가 나온다
		http.authorizeHttpRequests(auth -> auth
				// .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() - 예전 버전
				// Preflight 요청허용을 위해 'OPTIONS 메소드 요청'을 모든 URL에서 허용 + 사용자 인증여부와 상관없이 허용
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				//OPTIONS메서드를 제외한 '모든 요청'에 대해서는 인증요구
				.anyRequest().authenticated());
		// 2. http 요청이 들어오면 인증을 위한 브라우저 기본 인증 팝업창(JS의 prompt형식)을 보여준다
		http.httpBasic(Customizer.withDefaults());

		// 3. Stateless session(서버가 세션을 기억하지 않고 토큰을 통해 독립적으로 처리) 설정
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// 4. csrf 비활성화
		// SB 3.1.x 부터는 Lambda식을 사용하거나 메서드참조 :: 를 사용하는 방식이 추천된다 http.csrf(AbstractHttpConfigurer::disable); 
		http.csrf(csrf -> csrf.disable()); 
		
		//5.  return 할 http 상태 최종 빌드
		return http.build();

		// 메소드 체이닝 버전 - http 중복 참조을 없애고 세미콜론 제거 최종 build형태 return
//      return 
//              http
//                  .authorizeHttpRequests(
//                      auth -> 
//                          auth
//							.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
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
