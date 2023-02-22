package com.zerobase.carrot_auction.mapper;

import com.zerobase.carrot_auction.dto.ProductDto;
import com.zerobase.carrot_auction.model.ProductSearchForm;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

	long selectListCount(ProductSearchForm parameter);

	List<ProductDto> selectList(ProductSearchForm parameter);
}
