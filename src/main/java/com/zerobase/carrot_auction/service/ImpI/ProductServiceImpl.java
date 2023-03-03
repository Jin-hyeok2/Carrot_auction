package com.zerobase.carrot_auction.service.ImpI;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zerobase.carrot_auction.Exception.ErrorCode;
import com.zerobase.carrot_auction.Exception.PagingException;
import com.zerobase.carrot_auction.Exception.ProductException;
import com.zerobase.carrot_auction.dto.request.ProductCreateRequest;
import com.zerobase.carrot_auction.dto.request.ProductSearchRequest;
import com.zerobase.carrot_auction.dto.response.ProductDto;
import com.zerobase.carrot_auction.mapper.ProductMapper;
import com.zerobase.carrot_auction.repository.DealRepository;
import com.zerobase.carrot_auction.repository.ProductRepository;
import com.zerobase.carrot_auction.repository.UserRepository;
import com.zerobase.carrot_auction.repository.entity.ProductEntity;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import com.zerobase.carrot_auction.repository.entity.code.Status;
import com.zerobase.carrot_auction.security.TokenProvider;
import com.zerobase.carrot_auction.service.ProductService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final TokenProvider tokenProvider;
	private final DealRepository dealRepository;

	@Override
	public ProductCreateRequest create(ProductCreateRequest parameter) {
		UserEntity seller = userRepository.findById(parameter.getSellerId())
			.orElseThrow(() -> new ProductException(ErrorCode.NOT_FOUND_USER));
		ProductEntity product = ProductEntity.builder()
			.seller(seller)
			.title(parameter.getTitle())
			.auctionYn(parameter.isAuctionYn())
			.siDo(parameter.getSiDo())
			.guGun(parameter.getGuGun())
			.price(parameter.getPrice())
			.description(parameter.getDescription())
			.endPeriod(parameter.getEndPeriod())
			.build();

		if (parameter.isAuctionYn()) {
			product.setStatus(Status.WAITING);
		} else {
			product.setStatus(Status.SALE);
		}
		productRepository.save(product);
		return parameter;
	}

	@Override
	public Page<ProductDto> productList(ProductSearchRequest productSearchForm) {
		if (productSearchForm.getPageNum() <= 0 | productSearchForm.getPageSize() <= 0) {
			throw new PagingException(ErrorCode.INVALID_PAGE_INFO);
		}
		PageHelper.startPage(productSearchForm);

		return productMapper.selectList(productSearchForm);
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void updateProductStatus() {
		List<ProductEntity> products = productRepository.findByStatusNot(Status.DEL);
		LocalDateTime now = LocalDateTime.now();
		for (ProductEntity product : products) {
			LocalDateTime endAt = product.getCreateAt().plusDays(product.getEndPeriod()).withHour(0)
				.withMinute(0).withSecond(0);
			if (now.isAfter(endAt)) {
				product.setStatus(Status.CLOSE);
				productRepository.save(product);
			}
		}
	}

	@Override
	public ProductDto productDetail(Long id) {
		ProductEntity product = productRepository.findById(id)
			.orElseThrow(() -> new ProductException(ErrorCode.NOT_FOUND_PRODUCT));
		if (product.getStatus() == Status.DEL) {
			throw new ProductException(ErrorCode.DELETE_PRODUCT);
		}
		return ProductDto.of(product);
	}

	@Override
	public void delete(String token, Long productId) {
		String userEmail = tokenProvider.getEmail(token.substring("Bearer ".length()));
		UserEntity user = userRepository.findByEmail(userEmail)
			.orElseThrow(() -> new ProductException(ErrorCode.NOT_FOUND_USER));
		ProductEntity product = productRepository.findById(productId)
			.orElseThrow(() -> new ProductException(ErrorCode.NOT_FOUND_PRODUCT));
		if (product.getStatus() == Status.DEL) {
			throw new ProductException(ErrorCode.DELETE_PRODUCT);
		} else if (product.getStatus() != Status.CLOSE) {
			throw new ProductException(ErrorCode.TRANSACTION_NOT_COMPLETED);
		}
		if (!(product.getSeller().getId().equals(user.getId()))) {
			throw new ProductException(ErrorCode.INVALID_USER);
		}
		product.setStatus(Status.DEL);
		dealRepository.deleteAllByProduct(product);
		productRepository.save(product);
	}
}
