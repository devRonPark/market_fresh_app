package com.market.service;

import org.springframework.stereotype.Service;

import com.market.domain.JoinForm;
import com.market.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;
	
	@Override
	public void join(JoinForm joinForm) throws Exception {
		userRepository.save(joinForm.toUser());
	}

}
