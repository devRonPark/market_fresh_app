package com.market.service;

import java.util.List;

import com.market.entity.Product;

public interface ProductService {
	List<Product> getProductListBy(Integer sortedType) throws Exception;

	Product getProductById(Long productId) throws Exception;
}
