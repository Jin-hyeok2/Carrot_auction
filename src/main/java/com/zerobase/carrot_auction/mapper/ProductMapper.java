package com.zerobase.carrot_auction.mapper;

import com.github.pagehelper.Page;
import com.zerobase.carrot_auction.dto.ProductDto;
import com.zerobase.carrot_auction.model.ProductSearchForm;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

	Page<ProductDto> selectList(ProductSearchForm parameter);
}
