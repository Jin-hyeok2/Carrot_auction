package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.dto.DealDto;
import com.zerobase.carrot_auction.exception.DealException;
import com.zerobase.carrot_auction.exception.ErrorCode;
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
        dealEntity.setPrice(dealDto.getPrice());

        DealEntity savedDeal = dealRepository.save(dealEntity);

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
}
