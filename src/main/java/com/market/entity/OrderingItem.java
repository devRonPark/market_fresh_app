package com.market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity(name = "ordering_item")
@Data
@RequiredArgsConstructor
public class OrderingItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "ordering_id")
	@ManyToOne
	private Ordering ordering;
	
	@JoinColumn(name = "product_id")
	@ManyToOne
	private Product product;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false)
	private int unitPrice;
	
	// 가상 컬럼
	@Transient
	private int totalPrice;
	
	@PostLoad
    private void calculateTotalPrice() {
        if (quantity != 0 && unitPrice != 0) {
            this.totalPrice = unitPrice * quantity;
        }
    }
}
