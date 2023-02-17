package com.zerobase.carrot_auction.dto;

import com.zerobase.carrot_auction.entity.Product;
import com.zerobase.carrot_auction.model.status;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class ProductDto {

	private Long id;
	private UserEntity customer;
	private UserEntity seller;
	private boolean isAuction;
	private status status;
	private String title;
	private String siDo;
	private String guGun;
	private int price;
	private String description;
	private int end_period;
	private LocalDateTime createAt;

	public static ProductDto of(Product product) {
		return ProductDto.builder()
			.id(product.getId())
			.customer(product.getCustomer())
			.seller(product.getSeller())
			.isAuction(product.isAuction())
			.status(product.getStatus())
			.title(product.getTitle())
			.siDo(product.getSiDo())
			.guGun(product.getGuGun())
			.price(product.getPrice())
			.description(product.getDescription())
			.end_period(product.getEnd_period())
			.createAt(product.getCreateAt())
			.build();
	}
}
