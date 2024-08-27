package com.market.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.market.domain.JoinFormDTO;
import com.market.domain.LoginFormDTO;
import com.market.entity.User;
import com.market.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final HttpSession httpSession;
	
	@Override
	public void join(JoinFormDTO joinForm) throws Exception {
		String plainPassword = joinForm.getPassword();
		String encodedPassword = bCryptPasswordEncoder.encode(plainPassword);
		joinForm.setPassword(encodedPassword);
		userRepository.save(joinForm.toUser());
	}

	@Override
	public void login(LoginFormDTO loginForm) throws Exception {
		// 이메일로 사용자 존재 여부 조회
		User exUser = userRepository.findByEmail(loginForm.getEmail()).orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다."));
		
		// 비밀번호 일치 여부 확인
		boolean isPasswordMatched = bCryptPasswordEncoder.matches(loginForm.getPassword(), exUser.getPassword());
		
		if (!isPasswordMatched) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
		}
		
		// 세션에 사용자 정보 저장
		httpSession.setAttribute("user", exUser);
	}

}
