package com.zerobase.carrot_auction.model;

import com.zerobase.carrot_auction.dto.ProductDto;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductListResponse {

	private final List<ProductDto> productList;
	private final long totalCount;

}
