package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.dto.Response;
import com.zerobase.carrot_auction.model.ProductForm;
import com.zerobase.carrot_auction.model.ProductListResponse;
import com.zerobase.carrot_auction.model.ProductSearchForm;
import com.zerobase.carrot_auction.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;


	@GetMapping("")
	public ResponseEntity<Response> list(ProductSearchForm productSearchForm) {
		ProductListResponse productListResponse = productService.productList(productSearchForm);
		return ResponseEntity.ok(new Response("success", productListResponse.getProductList()));
	}

	@PostMapping("/create")
	public ResponseEntity<Response> create(Long sellerId, @RequestBody ProductForm productForm) {
		ProductForm product = productService.create(sellerId, productForm);

		return ResponseEntity.ok(new Response("success", product));
	}


}
