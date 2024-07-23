package com.yoon.rest.webservices.restfulwebservices.jwt;

import static org.springframework.security.config.Customizer.withDefaults;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JwtSecurityConfig {

    //정적파일 접근 허용
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // (1) CSRF 보호를 비활성화합니다.
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS)) // (2) 세션을 사용하지 않고, 상태 없는(Stateless) 세션 정책을 설정합니다.
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers("/**",
                                                "/authenticate", "/actuator", "/actuator/*", "/static/**", "/index.html")
                                        .permitAll() // 지정된 URL들에 대한 요청을 모두 허용합니다.
                                        .requestMatchers("/h2-console/**")
                                        .permitAll() // H2 콘솔에 대한 요청을 모두 허용합니다.
                                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                                        .permitAll() // OPTIONS 메서드에 대한 요청을 모두 허용합니다.
                                        .anyRequest()
                                        .authenticated()) // 그 외의 모든 요청은 인증을 요구합니다.
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults())) // (4) OAuth2 리소스 서버를 설정하고, JWT를 사용하도록 합니다.
                .exceptionHandling(
                        (ex) ->
                                ex.authenticationEntryPoint(
                                                new BearerTokenAuthenticationEntryPoint()) // 인증이 필요한 리소스에 인증되지 않은 사용자가 접근할 때의 처리기를 설정합니다.
                                        .accessDeniedHandler(
                                                new BearerTokenAccessDeniedHandler())) // 접근이 거부된 경우의 처리기를 설정합니다.
                .httpBasic(
                        withDefaults()) // (5) HTTP Basic 인증을 설정합니다.
                .headers(header -> header.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin())) // 동일 출처에서의 프레임 사용을 허용합니다.
                .build();
    }

    // AuthenticationManager 빈 정의
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService) {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authenticationProvider);
    }
 // UserDetailsService 빈 정의
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("yoon")
                                .password("{noop}dummy")
                                .authorities("read")
                                .roles("USER")
                                .build();

        return new InMemoryUserDetailsManager(user);
    }

 // JwtEncoder 빈 정의
    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }
    // JwtDecoder 빈 정의
    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder
                .withPublicKey(rsaKey().toRSAPublicKey())
                .build();
    }
 // RSAKey 빈 정의
    @Bean
    public RSAKey rsaKey() {
        
        KeyPair keyPair = keyPair();
        
        return new RSAKey
                .Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }
 // KeyPair 빈 정의
    @Bean
    public KeyPair keyPair() {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException(
                    "RSA 키 쌍을 생성할 수 없습니다", e);
        }
    }
 // JWKSource 빈 정의
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        JWKSet jwkSet = new JWKSet(rsaKey());
        return (((jwkSelector, securityContext) 
                        -> jwkSelector.select(jwkSet)));
    }
    
}