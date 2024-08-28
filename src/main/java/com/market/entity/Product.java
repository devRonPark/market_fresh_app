package com.market.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.market.domain.ProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@RequiredArgsConstructor
public class Product {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private String categoryName;

    @Column
    private String brand;

    @Column
    private String manufacturer;

    @Column(name = "image_url")
    private String imageURL;

    @CreatedDate
    @Column(name = "added_date")
    private LocalDateTime addedDate = LocalDateTime.now();
	
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;
}
