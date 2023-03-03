package com.zerobase.carrot_auction.dto;

import com.zerobase.carrot_auction.exception.DealException;
import com.zerobase.carrot_auction.exception.ErrorCode;
import com.zerobase.carrot_auction.repository.entity.DealEntity;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DealDto {

	private Long id;
	private Long productId;
	private Long customerId;
	private int price;

	private LocalDateTime createAt;

	public DealDto(Long id, Long productId, Long customerId, int price, LocalDateTime createAt) {
		this.id = id;
		this.productId = productId;
		this.customerId = customerId;
		this.price = price;
		this.createAt = createAt;
	}


	public static DealDto fromEntity(DealEntity entity) {
		if (entity == null) {
			throw new DealException(ErrorCode.NOT_FOUND_DEAL);
		}
		return DealDto.builder()
				.id(entity.getId())
				.customerId(entity.getCustomer().getId())
				.productId(entity.getProduct().getId())
				.price(entity.getPrice())
				.build();

	}
}