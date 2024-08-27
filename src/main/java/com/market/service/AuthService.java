package com.market.service;

import com.market.domain.JoinFormDTO;
import com.market.domain.LoginFormDTO;

public interface AuthService {
	void join(JoinFormDTO joinForm) throws Exception;
	void login(LoginFormDTO loginForm) throws Exception;
}
