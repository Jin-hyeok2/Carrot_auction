package com.zerobase.carrot_auction.dto;

import com.zerobase.carrot_auction.model.Status;
import com.zerobase.carrot_auction.repository.entity.Product;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private Long id;
	private LocalDateTime createAt;
	private String description;
	private int endPeriod;
	private String guGun;
	private boolean isAuction;
	private int price;
	private String siDo;
	private Status status;
	private String title;
	private String customerNickname;
	private String sellerNickname;

	public static ProductDto of(Product product) {
		return ProductDto.builder()
			.id(product.getId())
			.customerNickname(product.getCustomerNickName())
			.sellerNickname(product.getSellerNickName())
			.isAuction(product.isAuction())
			.status(product.getStatus())
			.title(product.getTitle())
			.siDo(product.getSiDo())
			.guGun(product.getGuGun())
			.price(product.getPrice())
			.description(product.getDescription())
			.endPeriod(product.getEndPeriod())
			.createAt(product.getCreateAt())
			.build();
	}
}
