package com.market.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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

}
