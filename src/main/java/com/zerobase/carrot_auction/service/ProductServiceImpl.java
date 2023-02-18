package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.Exception.ErrorCode;
import com.zerobase.carrot_auction.Exception.ProductException;
import com.zerobase.carrot_auction.Exception.UserException;
import com.zerobase.carrot_auction.model.ProductForm;
import com.zerobase.carrot_auction.model.status;
import com.zerobase.carrot_auction.repository.ProductRepository;
import com.zerobase.carrot_auction.repository.UserRepository;
import com.zerobase.carrot_auction.repository.entity.Product;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public ProductForm add(Long sellerId, ProductForm parameter) {
		if (parameter.getGuGun().isEmpty() || parameter.getSiDo().isEmpty()) {
			throw new ProductException(ErrorCode.NOT_ENTER_REGION);
		} else if (parameter.getTitle().isEmpty()) {
			throw new ProductException(ErrorCode.NOT_ENTER_TITLE);
		} else if (parameter.getPrice() < 0) {
			throw new ProductException(ErrorCode.NEGATIVE_PRICE);
		} else if (parameter.getEndPeriod() < 0) {
			throw new ProductException(ErrorCode.NEGATIVE_END_PERIOD);
		}
		Optional<UserEntity> optionalUserEntity = userRepository.findById(sellerId);
		if (optionalUserEntity.isEmpty()) {
			throw new UserException(ErrorCode.NOT_FOUND_USER);
		}
		UserEntity seller = optionalUserEntity.get();
		Product product = Product.builder()
			.seller(seller)
			.title(parameter.getTitle())
			.isAuction(parameter.isAuctionYn())
			.status(status.판매중)
			.siDo(parameter.getSiDo())
			.guGun(parameter.getGuGun())
			.price(parameter.getPrice())
			.description(parameter.getDescription())
			.endPeriod(parameter.getEndPeriod())
			.build();
		productRepository.save(product);
		return parameter;
	}
}
