package com.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllByOrderByPriceAsc();
	List<Product> findAllByOrderByPriceDesc();
	List<Product> findAllByOrderByAddedDateDesc();
}
