package com.zerobase.carrot_auction.mapper;

import com.zerobase.carrot_auction.model.ProductSearchForm;
import com.zerobase.carrot_auction.repository.entity.Product;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

	long selectListCount(ProductSearchForm parameter);

	List<Product> selectList(ProductSearchForm parameter);
}
