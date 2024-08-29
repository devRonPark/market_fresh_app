package com.market.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.market.repository.CartRepository;
import com.market.service.CartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final CartRepository cartRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 로그인 전의 세션 ID를 가져옴
    	HttpSession session = request.getSession();
        String preLoginSessionId = (String) session.getAttribute("PRE_LOGIN_SESSION_ID");

        /// 사용자 ID 가져오기
        Long userId = (authentication != null && authentication.getPrincipal() instanceof com.market.entity.User)
        		? ((com.market.entity.User) authentication.getPrincipal()).getId()
        				: null;
        if (preLoginSessionId != null) {
            // 쇼핑 카트의 userId를 업데이트
            cartRepository.updateUserIdBySessionId(userId, preLoginSessionId);
        }

        // / 페이지로 리다이렉션
        response.sendRedirect("/");
    }
}

