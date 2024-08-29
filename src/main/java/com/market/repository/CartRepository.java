package com.market.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.market.domain.ShoppingCartStatus;
import com.market.entity.ShoppingCart;

import jakarta.transaction.Transactional;

@Repository
public interface CartRepository extends JpaRepository<ShoppingCart, Long> {
	Optional<ShoppingCart> findByUserIdAndStatus(Long userId, ShoppingCartStatus active);
	Optional<ShoppingCart> findBySessionIdAndStatus(String sessionId, ShoppingCartStatus active);
	Optional<ShoppingCart> findByUserIdAndStatusOrSessionIdAndStatus(Long userId, ShoppingCartStatus status, String sessionId, ShoppingCartStatus status2);
	
	@Modifying
    @Transactional
    @Query("UPDATE shopping_cart ct SET ct.user.id = :userId WHERE ct.sessionId = :sessionId")
    void updateUserIdBySessionId(@Param("userId") Long userId, @Param("sessionId") String sessionId);

}
