package com.zerobase.carrot_auction.repository.entity;

import com.sun.istack.NotNull;
import com.zerobase.carrot_auction.repository.entity.code.Status;
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
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "product")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = true)
	private UserEntity customer;
	@ManyToOne
	@JoinColumn(name = "seller_id", nullable = false)
	private UserEntity seller;
	@NotNull
	private boolean auctionYn;
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

	public String getSellerNickName() {
		return seller.getNickname();
	}

	public String getCustomerNickName() {
		if (customer != null) {
			return customer.getNickname();
		}
		return null;
	}
}
