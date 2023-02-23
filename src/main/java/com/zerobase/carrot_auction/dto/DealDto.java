package com.zerobase.carrot_auction.dto;

import com.zerobase.carrot_auction.exception.DealException;
import com.zerobase.carrot_auction.exception.ErrorCode;
import com.zerobase.carrot_auction.repository.entity.DealEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealDto {
    private Long id;
    private Long productId;
    private Long customerId;
    private int price;

    public DealDto(Long id, Long productId, Long customerId, int price) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.price = price;
    }
    public static DealDto fromEntity(DealEntity entity) {
        if (entity == null) {
            throw new DealException(ErrorCode.NOT_FOUND_DEAL);
        }
        return new DealDto(
                entity.getId(),
                entity.getProduct().getId(),
                entity.getCustomer().getId(),
                entity.getPrice()
        );
    }
}