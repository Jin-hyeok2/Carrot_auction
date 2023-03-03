package com.zerobase.carrot_auction.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zerobase.carrot_auction.dto.request.ProductCreateRequest;
import com.zerobase.carrot_auction.dto.request.ProductSearchRequest;
import com.zerobase.carrot_auction.dto.response.ProductDto;
import com.zerobase.carrot_auction.dto.response.Response;
import com.zerobase.carrot_auction.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"거래글 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    @ApiOperation(value = "거래글 조회")
    public ResponseEntity<Response> list(ProductSearchRequest productSearchForm) {
        Page<ProductDto> productDtos = productService.productList(productSearchForm);

        return ResponseEntity.ok(new Response("success", PageInfo.of(productDtos)));
    }

    @GetMapping("{id}/detail")
    @ApiOperation(value = "거래글 상세보기")
    public ResponseEntity<Response> detail(@PathVariable(name = "id") Long productId) {
        ProductDto detail = productService.productDetail(productId);
        return ResponseEntity.ok(new Response("success", detail));
    }

    @PostMapping("/create")
    @ApiOperation(value = "거래글 작성")
    public ResponseEntity<Response> create(
            final @Valid @RequestBody ProductCreateRequest productForm) {
        ProductCreateRequest product = productService.create(productForm);

        return ResponseEntity.ok(new Response("success", "거래글이 등록되었습니다."));
    }

    @DeleteMapping("{id}/delete")
    @ApiOperation(value = "거래글 삭제")
    public ResponseEntity<Response> delete(@RequestHeader(name = "Authorization") String token,
                                           @PathVariable(name = "id") Long productId) {
        productService.delete(token, productId);
        return ResponseEntity.ok(new Response("success", "삭제되었습니다."));
    }


}
