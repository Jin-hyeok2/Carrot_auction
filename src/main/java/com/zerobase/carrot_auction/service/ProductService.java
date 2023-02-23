package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.model.ProductForm;
import com.zerobase.carrot_auction.model.ProductListResponse;
import com.zerobase.carrot_auction.model.ProductSearchForm;

public interface ProductService {

	ProductForm create(Long sellerId, ProductForm parameter);

	ProductListResponse productList(ProductSearchForm productSearchForm);
}
