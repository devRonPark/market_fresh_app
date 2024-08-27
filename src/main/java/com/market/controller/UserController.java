package com.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {
	@GetMapping(value = {"", "/"})
	public String mainPage() {
		return "main";
	}
	
	@GetMapping("/join")
	public String joinPage() {
		return "join";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
}
