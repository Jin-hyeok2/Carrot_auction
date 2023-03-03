package com.zerobase.carrot_auction.service;

import com.github.pagehelper.Page;
import com.zerobase.carrot_auction.dto.request.ProductCreateRequest;
import com.zerobase.carrot_auction.dto.request.ProductSearchRequest;
import com.zerobase.carrot_auction.dto.response.ProductDto;

public interface ProductService {

    ProductCreateRequest create(ProductCreateRequest parameter);

    Page<ProductDto> productList(ProductSearchRequest productSearchForm);

    ProductDto productDetail(Long id);

    void delete(String token, Long id);

}
