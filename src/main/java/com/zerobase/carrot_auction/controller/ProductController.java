package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.dto.ProductDto;
import com.zerobase.carrot_auction.dto.Response;
import com.zerobase.carrot_auction.model.ProductForm;
import com.zerobase.carrot_auction.model.ProductSearchForm;
import com.zerobase.carrot_auction.service.ProductService;
import java.util.List;
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
		List<ProductDto> productList = productService.productList(productSearchForm);
		System.out.println(productList.toArray().length);
		return ResponseEntity.ok(new Response("success", productList));
	}

	@PostMapping("/create")
	public ResponseEntity<Response> create(Long sellerId, @RequestBody ProductForm productForm) {
		ProductForm product = productService.create(sellerId, productForm);

		return ResponseEntity.ok(new Response("success", product));
	}


}
