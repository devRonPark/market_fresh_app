package com.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.market.domain.JoinForm;
import com.market.service.AuthService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@GetMapping("/join")
	public ModelAndView joinPage() {
		return new ModelAndView("join", "joinForm", new JoinForm());
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@PostMapping("/auth/join")
	public ModelAndView join(@Validated JoinForm joinForm, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return new ModelAndView("join", "result", result);
		}

		if (!joinForm.isPasswordMatching()) {
			result.rejectValue("passwordConfirm", "error.joinForm", "비밀번호가 일치하지 않습니다.");
			return new ModelAndView("join", "result", result);
		}
		authService.join(joinForm);
		return new ModelAndView("redirect:/login");
	}
}
