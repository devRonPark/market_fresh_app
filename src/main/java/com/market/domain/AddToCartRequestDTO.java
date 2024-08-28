package com.market.domain;

import com.market.entity.CartItem;
import com.market.entity.Product;
import com.market.entity.ShoppingCart;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddToCartRequestDTO {
	private Long productId;
	private Integer quantity;

	public CartItem toCartItem(ShoppingCart shoppingCart, Product product) {
		return CartItem.builder().shoppingCart(shoppingCart).product(product).quantity(quantity)
				.unitPrice(product.getPrice()).build();
	}
}
