package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.Exception.ErrorCode;
import com.zerobase.carrot_auction.Exception.ProductException;
import com.zerobase.carrot_auction.entity.Product;
import com.zerobase.carrot_auction.model.ProductForm;
import com.zerobase.carrot_auction.repository.ProductRepository;
import com.zerobase.carrot_auction.repository.UserRepository;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public ResponseEntity<String> add(Long sellerId, ProductForm parameter) {
		if (parameter.getGuGun().isEmpty() || parameter.getSiDo().isEmpty()) {
			throw new ProductException(ErrorCode.NOT_ENTER_REGION);
		} else if (parameter.getTitle().isEmpty()) {
			throw new ProductException(ErrorCode.NOT_ENTER_TITLE);
		} else if (parameter.getPrice() < 0) {
			throw new ProductException(ErrorCode.NEGATIVE_PRICE);
		} else if (parameter.getEnd_period() < 0) {
			throw new ProductException(ErrorCode.NEGATIVE_END_PERIOD);
		}
		Optional<UserEntity> optionalUserEntity = userRepository.findById(sellerId);
		if (optionalUserEntity.isEmpty()) {
			throw new RuntimeException("유저가 없습니다.");
		}
		UserEntity seller = optionalUserEntity.get();
		Product product = Product.builder()
			.seller(seller)
			.title(parameter.getTitle())
			.isAuction(parameter.isAuction())
			.siDo(parameter.getSiDo())
			.guGun(parameter.getGuGun())
			.price(parameter.getPrice())
			.description(parameter.getDescription())
			.end_period(parameter.getEnd_period())
			.build();
		productRepository.save(product);
		return ResponseEntity.ok("거래글 등록이 완료되었습니다.");
	}
}
