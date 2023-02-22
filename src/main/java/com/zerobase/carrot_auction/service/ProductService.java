package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.dto.ProductDto;
import com.zerobase.carrot_auction.model.ProductForm;
import com.zerobase.carrot_auction.model.ProductSearchForm;
import java.util.List;

public interface ProductService {

	ProductForm create(Long sellerId, ProductForm parameter);

	List<ProductDto> productList(ProductSearchForm productSearchForm);
}
