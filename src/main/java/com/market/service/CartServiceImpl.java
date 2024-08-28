package com.market.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.market.domain.RemoveCartItemRequestDTO;
import com.market.domain.ShoppingCartStatus;
import com.market.entity.CartItem;
import com.market.entity.ShoppingCart;
import com.market.entity.User;
import com.market.repository.CartItemRepository;
import com.market.repository.CartRepository;
import com.market.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;
	
	@Override
	@Transactional
	public ShoppingCart findOrCreateShoppingCart(Long userId, String sessionId) throws Exception {
		Optional<ShoppingCart> optCart;
		
		if (userId != null) {
			// 회원인 경우,
			optCart = cartRepository.findByUserIdAndStatus(userId, ShoppingCartStatus.ACTIVE);
		} else {
			// 비회원인 경우,
			optCart = cartRepository.findBySessionIdAndStatus(sessionId, ShoppingCartStatus.ACTIVE);
		}
		
		return optCart.orElseGet(() -> {
			ShoppingCart newCart = new ShoppingCart();
			
			if (userId != null) {
				User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Id 가 유효하지 않습니다."));
				newCart.setUser(user);
			} else {
				newCart.setSessionId(sessionId);
			}
			newCart.setStatus(ShoppingCartStatus.ACTIVE);
			return cartRepository.save(newCart);
		});
	}

	@Override
	public void addCartItem(CartItem cartItem) throws Exception {
		ShoppingCart existingCart = cartItem.getShoppingCart();
		Optional<CartItem> existingCartItem = cartItemRepository.findByShoppingCartAndProduct(existingCart, cartItem.getProduct());
		
		if (existingCartItem.isPresent()) {
			// 수량 업데이트
			CartItem existingItem = existingCartItem.get();
			existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
			existingItem.setTotalPrice(existingItem.getQuantity() * existingItem.getUnitPrice());
			cartItemRepository.save(existingItem);
		} else {
			cartItemRepository.save(cartItem);
		}
		
	}

	@Override
	@Transactional
	public List<CartItem> getCartItemList(Long userId, String sessionId) throws Exception {
		Optional<ShoppingCart> optCart;
		
		// 회원
		if (userId != null) {
			optCart = cartRepository.findByUserIdAndStatus(userId, ShoppingCartStatus.ACTIVE);
		} else { // 비회원
			optCart = cartRepository.findBySessionIdAndStatus(sessionId, ShoppingCartStatus.ACTIVE);
		}
		
		if (!optCart.isPresent()) {
			throw new RuntimeException();
		}
		else {
			ShoppingCart cart = optCart.get();			
			List<CartItem> cartItemList = cartItemRepository.findAllByShoppingCart(cart);		
			return cartItemList;			
		}
		
	}

	@Override
	@Transactional
	public void removeItemFromCart(Long userId, String sessionId, RemoveCartItemRequestDTO requestDTO)
			throws Exception {
		Optional<ShoppingCart> existingOptCart;
		
		if (userId != null) {
			// 회원인 경우 userId를 기준으로 조회
			existingOptCart = cartRepository.findByUserIdAndStatus(userId, ShoppingCartStatus.ACTIVE);
			
		} else {
			// 비회원인 경우 sessionId를 기준으로 조회
			existingOptCart = cartRepository.findBySessionIdAndStatus(sessionId, ShoppingCartStatus.ACTIVE);			
		}
		
		
		if (!existingOptCart.isPresent()) {
			String message = userId != null 
	                ? String.format("사용자 ID %d에 대한 활성 장바구니를 찾을 수 없습니다.", userId)
	                : String.format("세션 ID %s에 대한 활성 장바구니를 찾을 수 없습니다.", sessionId);
	        throw new RuntimeException(message);
		}
		
		// 데이터베이스에서 해당 상품을 찾아 삭제
		// 조건에 따른 삭제 로직
		ShoppingCart existingCart = existingOptCart.get();
		System.out.println("-----------------------------");
		System.out.println(existingCart);
		System.out.println("-----------------------------");
		System.out.println(cartItemRepository.findByShoppingCartAndProductId(existingCart, requestDTO.getProductId()));
		
		int deletedCount = cartItemRepository.deleteByShoppingCartAndProductId(existingCart, requestDTO.getProductId());
		
		if (deletedCount == 0) {
	        // 상품이 장바구니에 존재하지 않을 경우
	        String message = String.format("장바구니에서 상품 ID %d를 찾을 수 없습니다.", requestDTO.getProductId());
	        throw new RuntimeException(message);
	    }
	}

}
