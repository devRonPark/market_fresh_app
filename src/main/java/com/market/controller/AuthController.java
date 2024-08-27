package com.market.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.market.domain.JoinFormDTO;
import com.market.domain.LoginFormDTO;
import com.market.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;

	@GetMapping("/join")
	public ModelAndView joinPage() {
		return new ModelAndView("join", "joinForm", new JoinFormDTO());
	}

	@GetMapping("/login")
	public ModelAndView loginPage() {
		return new ModelAndView("login", "loginForm", new LoginFormDTO());
	}

	@PostMapping("/join")
	public ModelAndView join(@ModelAttribute("joinForm") @Validated JoinFormDTO joinForm, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return new ModelAndView("join", "result", result);
		}

		if (!joinForm.isPasswordMatching()) {
			result.rejectValue("passwordConfirm", "error.joinForm", "비밀번호가 일치하지 않습니다.");
			return new ModelAndView("join", "result", result);
		}
		authService.join(joinForm);
		return new ModelAndView("redirect:/auth/login");
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") @Validated LoginFormDTO loginForm, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return "login";
		}
		authService.login(loginForm);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("로그아웃 요청");
		new SecurityContextLogoutHandler()
		    .logout(req, res, SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/";
	}
}
