package com.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.entity.CartItem;
import com.market.entity.Product;
import com.market.entity.ShoppingCart;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	Optional<CartItem> findByShoppingCartAndProduct(ShoppingCart existingCart, Product product);

	List<CartItem> findAllByShoppingCart(ShoppingCart cart);

	int deleteByShoppingCartAndProductId(ShoppingCart existingCart, Long productId);

	CartItem findByShoppingCartAndProductId(ShoppingCart existingCart, Long productId);


}
