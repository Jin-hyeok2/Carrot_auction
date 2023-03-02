package com.zerobase.carrot_auction.service;

import com.github.pagehelper.Page;
import com.zerobase.carrot_auction.dto.reponse.ProductDto;
import com.zerobase.carrot_auction.dto.request.ProductCreateRequest;
import com.zerobase.carrot_auction.dto.request.ProductSearchRequest;

public interface ProductService {

	ProductCreateRequest create(ProductCreateRequest parameter);

	Page<ProductDto> productList(ProductSearchRequest productSearchForm);

	ProductDto productDetail(Long id);

	void delete(String token, Long id);

}
