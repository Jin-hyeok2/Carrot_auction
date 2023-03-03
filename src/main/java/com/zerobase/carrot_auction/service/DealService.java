package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.dto.DealDto;
import com.zerobase.carrot_auction.exception.DealException;
import com.zerobase.carrot_auction.exception.ErrorCode;
import com.zerobase.carrot_auction.repository.DealRepository;
import com.zerobase.carrot_auction.repository.ProductRepository;
import com.zerobase.carrot_auction.repository.UserRepository;
import com.zerobase.carrot_auction.repository.entity.DealEntity;
import com.zerobase.carrot_auction.repository.entity.ProductEntity;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import com.zerobase.carrot_auction.repository.entity.code.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class DealService {


    private final DealRepository dealRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public DealDto createDeal(DealDto dealDto) {
        ProductEntity product = productRepository.findById(dealDto.getProductId())
                .orElseThrow(() -> new DealException(ErrorCode.NOT_FOUND_PRODUCT));
        UserEntity customer = userRepository.findById(dealDto.getCustomerId())
                .orElseThrow(() -> new DealException(ErrorCode.NOT_FOUND_USER));


        DealEntity dealEntity = DealEntity.builder()
                .product(product)
                .customer(customer)
                .build();

        if (product.getStatus() == Status.DEL) {
            throw new DealException(ErrorCode.DELETE_PRODUCT);
        }

        if (product.isAuctionYn()) { // 경매거래인 경우
            if (dealDto.getPrice() <= product.getPrice()) {
                throw new DealException(ErrorCode.INVALID_PRICE);
            }
            dealEntity.setPrice(dealDto.getPrice());
            product.setPrice(dealDto.getPrice());
            product.setStatus(Status.BIDDING);
        } else {
            dealEntity.setPrice(product.getPrice());
        }

        DealEntity savedDeal = dealRepository.save(dealEntity);
        productRepository.save(product);

        return DealDto.fromEntity(savedDeal);
    }

    public DealDto getDeal(Long dealId) {
        DealEntity dealEntity = dealRepository.findById(dealId)
                .orElseThrow(() -> new DealException(ErrorCode.NOT_FOUND_DEAL));
        return DealDto.fromEntity(dealEntity);
    }

    public DealDto updateDeal(Long dealId, DealDto dealDto) {

        DealEntity dealEntity = dealRepository.findById(dealId)
                .orElseThrow(() -> new DealException(ErrorCode.NOT_FOUND_DEAL));
        dealEntity.setPrice(dealDto.getPrice());
        DealEntity updatedDeal = dealRepository.save(dealEntity);
        return DealDto.fromEntity(updatedDeal);
    }

    public void deleteDeal(Long dealId) {
        dealRepository.deleteById(dealId);
    }

    public void completeDeal(Long dealId, Long sellerId) {
        DealEntity deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new DealException(ErrorCode.NOT_FOUND_DEAL));
        ProductEntity product = deal.getProduct();

        // 거래가 판매 중인 상태인지 확인
        if (product.getStatus() != Status.SALE) {
            throw new DealException(ErrorCode.INVALID_PRODUCT_STATUS);
        }

        // 거래의 판매자가 맞는지 확인
        if (!deal.getProduct().getSeller().getId().equals(sellerId)) {
            throw new DealException(ErrorCode.INVALID_USER);
        }

        product.setStatus(Status.CLOSE);
        product.setCustomer(deal.getCustomer());
        dealRepository.save(deal);
        productRepository.save(product);
    }
}
