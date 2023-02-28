package com.zerobase.carrot_auction.service;

import com.github.pagehelper.Page;
import com.zerobase.carrot_auction.dto.ProductDto;
import com.zerobase.carrot_auction.model.ProductForm;
import com.zerobase.carrot_auction.model.ProductSearchForm;

public interface ProductService {

	ProductForm create(ProductForm parameter);

	Page<ProductDto> productList(ProductSearchForm productSearchForm);

	ProductDto productDetail(Long id);

	void delete(String token, Long id);

}
