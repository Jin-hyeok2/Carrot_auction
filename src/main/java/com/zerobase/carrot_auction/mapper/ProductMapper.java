package com.zerobase.carrot_auction.mapper;

import com.github.pagehelper.Page;
import com.zerobase.carrot_auction.dto.request.ProductSearchRequest;
import com.zerobase.carrot_auction.dto.response.ProductDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

	Page<ProductDto> selectList(ProductSearchRequest parameter);
}
