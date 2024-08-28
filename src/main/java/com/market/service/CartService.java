package com.market.service;

import java.util.List;

import com.market.domain.RemoveCartItemRequestDTO;
import com.market.domain.RemoveSelectedCartItemsRequestDTO;
import com.market.entity.CartItem;
import com.market.entity.ShoppingCart;

public interface CartService {
	ShoppingCart findOrCreateShoppingCart(Long userId, String sessionId) throws Exception;

	void addCartItem(CartItem cartItem) throws Exception;

	List<CartItem> getCartItemList(Long userId, String sessionId) throws Exception;

	void removeItemFromCart(Long userId, String sessionId, RemoveCartItemRequestDTO requestDTO) throws Exception;

	void removeItemsFromCart(Long userId, String sessionId, RemoveSelectedCartItemsRequestDTO requestDTO) throws Exception;
}
