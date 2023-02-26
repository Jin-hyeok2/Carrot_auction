package com.zerobase.carrot_auction.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zerobase.carrot_auction.dto.ProductDto;
import com.zerobase.carrot_auction.dto.Response;
import com.zerobase.carrot_auction.model.ProductForm;
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
		Page<ProductDto> productDtos = productService.productList(productSearchForm);
		return ResponseEntity.ok(new Response("success", PageInfo.of(productDtos)));
	}

	@GetMapping("/{productId}")
	public ResponseEntity<Response> detail(ProductSearchForm productSearchForm) {
		ProductDto detail = productService.productDetail(productSearchForm.getProductId());
		return ResponseEntity.ok(new Response("success", detail));
	}

	@PostMapping("/create")
	public ResponseEntity<Response> create(Long sellerId, @RequestBody ProductForm productForm) {
		ProductForm product = productService.create(sellerId, productForm);

		return ResponseEntity.ok(new Response("success", product));
	}


}
