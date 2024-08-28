package com.market.domain;

import lombok.Data;

@Data
public class RemoveCartItemRequestDTO {
	private Long cartId;
	private Long productId;
}
