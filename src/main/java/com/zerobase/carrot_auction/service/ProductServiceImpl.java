package com.zerobase.carrot_auction.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zerobase.carrot_auction.exception.ErrorCode;
import com.zerobase.carrot_auction.exception.PagingException;
import com.zerobase.carrot_auction.exception.ProductException;
import com.zerobase.carrot_auction.dto.ProductDto;
import com.zerobase.carrot_auction.mapper.ProductMapper;
import com.zerobase.carrot_auction.model.ProductForm;
import com.zerobase.carrot_auction.model.ProductSearchForm;
import com.zerobase.carrot_auction.model.Status;
import com.zerobase.carrot_auction.repository.ProductRepository;
import com.zerobase.carrot_auction.repository.UserRepository;
import com.zerobase.carrot_auction.repository.entity.Product;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	public ProductForm create(Long sellerId, ProductForm parameter) {
		if (parameter.getGuGun().isEmpty() || parameter.getSiDo().isEmpty()) {
			throw new ProductException(ErrorCode.NOT_ENTER_REGION);
		} else if (parameter.getTitle().isEmpty()) {
			throw new ProductException(ErrorCode.NOT_ENTER_TITLE);
		} else if (parameter.getPrice() < 0) {
			throw new ProductException(ErrorCode.NEGATIVE_PRICE);
		} else if (parameter.getEndPeriod() < 0) {
			throw new ProductException(ErrorCode.NEGATIVE_END_PERIOD);
		}
		UserEntity seller = userRepository.findById(sellerId)
			.orElseThrow(() -> new ProductException(ErrorCode.NOT_FOUND_USER));

		Product product = Product.builder()
			.seller(seller)
			.title(parameter.getTitle())
			.isAuction(parameter.isAuctionYn())
			.status(Status.판매중)
			.siDo(parameter.getSiDo())
			.guGun(parameter.getGuGun())
			.price(parameter.getPrice())
			.description(parameter.getDescription())
			.endPeriod(parameter.getEndPeriod())
			.build();
		productRepository.save(product);
		return parameter;
	}

	@Override
	public Page<ProductDto> productList(ProductSearchForm productSearchForm) {
		if (productSearchForm.getPageNum() <= 0) {
			throw new PagingException(ErrorCode.INVALID_PAGE_INFO);
		} else if (productSearchForm.getPageSize() <= 0) {
			throw new PagingException(ErrorCode.INVALID_PAGE_INFO);
		}
		PageHelper.startPage(productSearchForm);

		return productMapper.selectList(productSearchForm);
	}
	@Scheduled(cron = "0 0 0 * * *")
	public void updateProductStatus() {
		List<Product> products = productRepository.findByStatusNot(Status.거래종료);
		LocalDateTime now = LocalDateTime.now();
		for (Product product : products) {
			LocalDateTime endAt = product.getCreateAt().plusDays(product.getEndPeriod()).withHour(0).withMinute(0).withSecond(0);
			if (now.isAfter(endAt)) {
				product.setStatus(Status.거래종료);
				productRepository.save(product);
			}
		}
	}

	@Override
	public ProductDto productDetail(Long id) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new ProductException(ErrorCode.NOT_FOUND_PRODUCT));
		if (product.getStatus() == Status.삭제됨) {
			throw new ProductException(ErrorCode.DELETE_PRODUCT);
		}
		return ProductDto.of(product);
	}
}
