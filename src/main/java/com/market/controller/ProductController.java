package com.market.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.market.entity.Product;
import com.market.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@GetMapping("/{productId}")
	public String productDetailPage(@PathVariable("productId") Long productId, Model model) throws Exception {
		Product product = productService.getProductById(productId);
		model.addAttribute("product", product);
		return "productDetail";
	}
}
