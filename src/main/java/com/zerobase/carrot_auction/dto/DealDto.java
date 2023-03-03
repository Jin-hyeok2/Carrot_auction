package com.zerobase.carrot_auction.dto;

import com.zerobase.carrot_auction.exception.DealException;
import com.zerobase.carrot_auction.exception.ErrorCode;
import com.zerobase.carrot_auction.repository.entity.DealEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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
        return new DealDto(
                entity.getId(),
                entity.getProduct().getId(),
                entity.getCustomer().getId(),
                entity.getPrice(),
                entity.getCreateAt()
        );
    }
}