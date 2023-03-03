package com.zerobase.carrot_auction.repository.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "deal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class DealEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 거래 id

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_Id", nullable = false)
	private ProductEntity product; // 경매 글 id

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_Id", nullable = false)
	private UserEntity customer; // 구매자 id

	private int price; // 가격

	@CreatedDate
	private LocalDateTime createAt; // 거래 신청시간


	public DealEntity(ProductEntity product, UserEntity customer, int price,
		LocalDateTime createAt) {
		this.product = product;
		this.customer = customer;
		this.price = price;
		this.createAt = createAt;
	}

}