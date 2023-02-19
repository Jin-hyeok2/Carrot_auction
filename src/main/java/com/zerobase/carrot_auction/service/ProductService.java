package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.model.ProductForm;

public interface ProductService {

	ProductForm add(Long sellerId, ProductForm parameter);
}
