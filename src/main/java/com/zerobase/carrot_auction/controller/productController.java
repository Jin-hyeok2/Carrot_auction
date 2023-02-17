package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.dto.Response;
import com.zerobase.carrot_auction.model.ProductForm;
import com.zerobase.carrot_auction.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class productController {

	private final ProductService productService;

	@PostMapping("/product/add")
	public ResponseEntity<Response> create(Long sellerId, @RequestBody ProductForm productForm) {
		ProductForm product = productService.add(sellerId, productForm);

		return ResponseEntity.ok(new Response("success", product));
	}
}
