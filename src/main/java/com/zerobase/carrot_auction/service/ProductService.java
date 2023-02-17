package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.model.ProductForm;
import org.springframework.http.ResponseEntity;

public interface ProductService {

	ResponseEntity<String> add(Long sellerId, ProductForm parameter);
}
