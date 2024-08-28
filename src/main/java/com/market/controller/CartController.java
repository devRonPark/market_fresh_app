package com.market.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.market.domain.AddToCartRequestDTO;
import com.market.entity.CartItem;
import com.market.entity.Product;
import com.market.entity.ShoppingCart;
import com.market.service.CartService;
import com.market.service.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
	private final ProductService productService;
	private final CartService cartService;
	
	// 비회원이면 sessionId 가 필요하고, 회원이면 userId 가 필요하다.
	@GetMapping(value = {"", "/"})
	public String cartListPage(Model model, HttpSession session) throws Exception {
		// 사용자 ID 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long userId = (authentication != null && authentication.getPrincipal() instanceof User)
				? ((com.market.entity.User) authentication.getPrincipal()).getId()
						: null;
		
		// HttpSession 에서 세션 ID 가져오기
		String sessionId = session.getId();
		System.out.println(sessionId);
		List<CartItem> cartItemList = cartService.getCartItemList(userId, sessionId);
		
		model.addAttribute("cartItemList", cartItemList);
		return "cartList";
	}
	
	@PostMapping("/add")
	public String addToCart(AddToCartRequestDTO requestDTO, Model model, HttpSession session) throws Exception {
		// 사용자 ID 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (authentication != null && authentication.getPrincipal() instanceof User)
                ? ((com.market.entity.User) authentication.getPrincipal()).getId()
                : null;

        // HttpSession에서 세션 ID 가져오기
        String sessionId = session.getId();
        
		try {
			// 상품 조회
			Product product = productService.getProductById(requestDTO.getProductId());
			
			// 장바구니 찾기 또는 생성
			ShoppingCart shoppingCart = cartService.findOrCreateShoppingCart(userId, sessionId);
			
			// 장바구니 항목 추가
			CartItem cartItem = requestDTO.toCartItem(shoppingCart, product);
			cartService.addCartItem(cartItem);
			
			model.addAttribute("successMessage", "장바구니에 상품이 추가되었습니다.");			
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 추가 중 오류가 발생했습니다.");			
		}
		
		return "main";
	}
}
