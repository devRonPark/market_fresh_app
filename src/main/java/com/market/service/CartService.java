package com.market.service;

import java.util.List;

import com.market.entity.CartItem;
import com.market.entity.ShoppingCart;

public interface CartService {
	ShoppingCart findOrCreateShoppingCart(Long userId, String sessionId) throws Exception;

	void addCartItem(CartItem cartItem) throws Exception;

	List<CartItem> getCartItemList(Long userId, String sessionId) throws Exception;
}
