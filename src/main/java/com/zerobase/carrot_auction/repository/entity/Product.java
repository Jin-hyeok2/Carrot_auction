package com.zerobase.carrot_auction.repository.entity;

import com.sun.istack.NotNull;
import com.zerobase.carrot_auction.model.Status;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "customerId", nullable = true)
	private UserEntity customer;
	@ManyToOne
	@JoinColumn(name = "sellerId", nullable = false)
	private UserEntity seller;
	@NotNull
	private boolean isAuction;
	@NotNull
	@Enumerated(value = EnumType.STRING)
	private Status status;
	private String title;
	private String siDo;
	private String guGun;
	private int price;
	private String description;
	private int endPeriod;
	@CreatedDate
	private LocalDateTime createAt;

	public Long getSellerId() {
		return seller.getId();
	}

	public Long getCustomerId() {
		return customer.getId();
	}
}
