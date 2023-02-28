package com.zerobase.carrot_auction.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zerobase.carrot_auction.dto.ProductDto;
import com.zerobase.carrot_auction.dto.Response;
import com.zerobase.carrot_auction.model.ProductForm;
import com.zerobase.carrot_auction.model.ProductSearchForm;
import com.zerobase.carrot_auction.service.ProductService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

	@GetMapping("{id}/detail")
	public ResponseEntity<Response> detail(@PathVariable(name = "id") Long productId) {
		ProductDto detail = productService.productDetail(productId);
		return ResponseEntity.ok(new Response("success", detail));
	}

	@PostMapping("/create")
	public ResponseEntity<Response> create(final @Valid @RequestBody ProductForm productForm) {
		ProductForm product = productService.create(productForm);

		return ResponseEntity.ok(new Response("success", "거래글이 등록되었습니다."));
	}

	@DeleteMapping("{id}/delete")
	public ResponseEntity<Response> delete(@RequestHeader(name = "Authorization") String token,
		@PathVariable(name = "id") Long productId) {
		productService.delete(token, productId);
		return ResponseEntity.ok(new Response("success", "삭제되었습니다."));
	}


}
