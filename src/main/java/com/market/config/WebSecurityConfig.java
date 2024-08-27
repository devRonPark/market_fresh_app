package com.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final UserDetailsService userDetailsService;
	
	// 특정 부분에 스프링 시큐리티 기능 비활성화
	@Bean
	WebSecurityCustomizer configure() {
		return web ->
			web.ignoring().requestMatchers(new AntPathRequestMatcher("/static/**/"));
	}
	
	// 인증 관리자 설정
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCEncoder, UserDetailsService uds) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(bCEncoder);
		return new ProviderManager(authProvider);
	}
	
	// 암호화 빈
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	// HTTP 요청에 따른 보안 구성
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
			auth -> 
				auth.requestMatchers(
					new AntPathRequestMatcher("/"),
					new AntPathRequestMatcher("/auth/login"),
					new AntPathRequestMatcher("/auth/logout"),
					new AntPathRequestMatcher("/err"),
					new AntPathRequestMatcher("/auth/join"),
					new AntPathRequestMatcher("/product/**")
				).permitAll()
				.requestMatchers(
					new AntPathRequestMatcher("/admin/**")
				).hasRole("ADMIN")
				.anyRequest().authenticated()
			);
		
		http.formLogin(form -> form
				.loginPage("/")
				.loginProcessingUrl("/auth/login")
				.defaultSuccessUrl("/", true));
		
		http.logout(logout -> logout
				.logoutUrl("/auth/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true));
		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(AbstractHttpConfigurer::disable);
		
		return http.getOrBuild();
	}
}
