package com.market.service;

import com.market.domain.JoinForm;

public interface AuthService {
	void join(JoinForm joinForm) throws Exception;
}
