package com.market.entity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity(name = "cart_item")
@EntityListeners(AuditingEntityListener.class)
@Data
@RequiredArgsConstructor
public class CartItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCart shoppingCart;
	
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Column(nullable = false)
    private int quantity = 0;
    
    @Column(nullable = false)
	private int unitPrice;
    
    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;
    
    // 가상 컬럼
 	@Transient
 	private int totalPrice;
 	
 	@PostLoad
     private void calculateTotalPrice() {
         if (quantity != 0 && unitPrice != 0) {
             this.totalPrice = unitPrice * quantity;
         }
     }
    
    @PrePersist
    protected void onCreate() {
        this.addedAt = LocalDateTime.now();
    }
}
