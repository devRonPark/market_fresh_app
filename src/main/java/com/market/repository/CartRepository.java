package com.market.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.market.domain.ShoppingCartStatus;
import com.market.entity.ShoppingCart;

@Repository
public interface CartRepository extends JpaRepository<ShoppingCart, Long> {
	Optional<ShoppingCart> findByUserIdAndStatus(Long userId, ShoppingCartStatus active);
	Optional<ShoppingCart> findBySessionIdAndStatus(String sessionId, ShoppingCartStatus active);

}
