package com.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public String home() {
        // 메인 페이지를 반환하는 로직
        return "main"; // Thymeleaf 템플릿 파일명 (예: index.html)
    }
}