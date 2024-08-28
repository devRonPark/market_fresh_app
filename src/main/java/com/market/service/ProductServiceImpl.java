package com.market.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.market.entity.Product;
import com.market.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	
	@Override
	public List<Product> getProductListBy(Integer sortedType) throws Exception {
		if (sortedType == null) {
			return productRepository.findAllByOrderByAddedDateDesc();			
		}
		else {
			// 판매량순 정렬은 나중에 구현
			if (sortedType == 1) {
				// 낮은 가격순
				return productRepository.findAllByOrderByPriceDesc();
			} else if (sortedType == 2) {
				// 높은 가격순
				return productRepository.findAllByOrderByPriceAsc();
			}			
		}
		return productRepository.findAllByOrderByAddedDateDesc();
	}

	@Override
	public Product getProductById(Long productId) throws Exception {
		return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product Id 가 유효하지 않습니다."));
	}

}
