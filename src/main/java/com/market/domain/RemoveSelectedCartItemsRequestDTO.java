package com.market.domain;

import java.util.List;

import lombok.Data;

@Data
public class RemoveSelectedCartItemsRequestDTO {
	private Long cartId;
	private List<Long> productIds;
}
