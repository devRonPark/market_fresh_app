package com.market.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.market.domain.AddToCartRequestDTO;
import com.market.entity.Product;
import com.market.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
	private final ProductService productService;

    @GetMapping("/")
    public String home(@RequestParam(name = "sorted_type", required = false) Integer sortedType, Model model) throws Exception {
    	System.out.println("여기 호출");
    	List<Product> products = productService.getProductListBy(sortedType);
    	model.addAttribute("products", products);
        // 메인 페이지를 반환하는 로직
        return "main";
    }
}