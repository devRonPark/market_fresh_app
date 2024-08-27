package com.market.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.market.entity.User;
import com.market.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
	private final UserRepository userRepository;
	
	@Override
	public User loadUserByUsername(String username) {
		Optional<User> optUser = userRepository.findByEmail(username);
		User user = optUser.orElseThrow(()-> new IllegalArgumentException(username + " 없음"));
		return user;
	}
}
