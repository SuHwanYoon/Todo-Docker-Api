package com.yoon.rest.webservices.restfulwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FullStackRestfulWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullStackRestfulWebServicesApplication.class, args);
	}

	//크로스 오리진 리퀘스트를 전역으로(모든 엔드포인트에) 허용하기 위한 메서드
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				//모든 크로스 오리진 pattern 리퀘스트 허용
				registry.addMapping("/**")
					//모든 HTTP request 유형 허용
					.allowedMethods("*")
					.allowedOrigins("http://localhost:3000");
			}
		};
	}
}
