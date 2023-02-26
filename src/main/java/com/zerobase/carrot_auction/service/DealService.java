package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.dto.DealDto;
import com.zerobase.carrot_auction.exception.DealException;
import com.zerobase.carrot_auction.exception.ErrorCode;
import com.zerobase.carrot_auction.model.Status;
import com.zerobase.carrot_auction.repository.DealRepository;
import com.zerobase.carrot_auction.repository.ProductRepository;
import com.zerobase.carrot_auction.repository.UserRepository;
import com.zerobase.carrot_auction.repository.entity.DealEntity;
import com.zerobase.carrot_auction.repository.entity.Product;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class DealService {

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public DealDto createDeal(DealDto dealDto) {
        Product product = productRepository.findById(dealDto.getProductId())
                .orElseThrow(() ->  new DealException(ErrorCode.NOT_FOUND_PRODUCT));
        UserEntity customer = userRepository.findById(dealDto.getCustomerId())
                .orElseThrow(() -> new DealException(ErrorCode.NOT_FOUND_USER));

        DealEntity dealEntity = new DealEntity();
        dealEntity.setProduct(product);
        dealEntity.setCustomer(customer);

        if (product.isAuction()) { // 경매거래인 경우
            if (dealDto.getPrice() < product.getPrice()) {
                throw new DealException(ErrorCode.INVALID_PRICE);
            }
            dealEntity.setPrice(dealDto.getPrice());
            product.setPrice(dealDto.getPrice());
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
                .orElseThrow(() ->new DealException(ErrorCode.NOT_FOUND_DEAL));
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
        Product product = deal.getProduct();

        // 거래가 판매 중인 상태인지 확인
        if (product.getStatus() != Status.판매중) {
            throw new DealException(ErrorCode.INVALID_PRODUCT_STATUS);
        }

        // 거래의 판매자가 맞는지 확인
        if (!deal.getProduct().getSeller().getId().equals(sellerId)) {
            throw new DealException(ErrorCode.INVALID_USER);
        }

        product.setStatus(Status.거래종료);
        product.setCustomer(deal.getCustomer());
        dealRepository.save(deal);
        productRepository.save(product);
    }
}
