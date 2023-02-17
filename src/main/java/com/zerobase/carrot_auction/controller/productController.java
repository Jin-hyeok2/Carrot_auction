package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.model.ProductForm;
import com.zerobase.carrot_auction.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class productController {

	private final ProductService productService;

	@GetMapping("/product/add")
	public ResponseEntity<String> create(Long sellerId, @RequestBody ProductForm productForm) {
		return productService.add(sellerId, productForm);
	}
}
