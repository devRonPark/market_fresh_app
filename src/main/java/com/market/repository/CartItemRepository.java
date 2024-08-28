package com.market.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.market.entity.CartItem;
import com.market.entity.Product;
import com.market.entity.ShoppingCart;

import jakarta.transaction.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	Optional<CartItem> findByShoppingCartAndProduct(ShoppingCart existingCart, Product product);

	List<CartItem> findAllByShoppingCart(ShoppingCart cart);

	int deleteByShoppingCartAndProductId(ShoppingCart existingCart, Long productId);

	CartItem findByShoppingCartAndProductId(ShoppingCart existingCart, Long productId);

	// 특정 장바구니에서 주어진 상품 ID 목록에 해당하는 CartItem을 soft delete
	@Modifying
	@Transactional
	@Query("UPDATE cart_item ci SET ci.deletedAt = :deletedAt WHERE ci.shoppingCart.id = :shoppingCartId and ci.product.id IN :productIds")
	int softDeleteByShoppingCartIdAndProductIds(@Param("shoppingCartId") Long shoppingCartId, @Param("productIds") List<Long> productIds, @Param("deletedAt") LocalDateTime deletedAt);

	// deleted_at 이 null 인 항목만 조회
	@Query("SELECT ci FROM cart_item ci WHERE ci.shoppingCart.id = :shoppingCartId AND ci.deletedAt IS NULL")
	List<CartItem> findActiveCartItemsByShoppingCartId(@Param("shoppingCartId") Long shoppingCartId);


}
