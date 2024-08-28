package com.market.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.market.entity.Product;
import com.market.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final ProductService productService;

    @GetMapping("/")
    public String home(@RequestParam("sorted_type") int sortedType,Model model) throws Exception {
    	List<Product> products = productService.getProductListBy(sortedType);
    	model.addAttribute("products", products);
        // 메인 페이지를 반환하는 로직
        return "main";
    }
}