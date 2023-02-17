package com.zerobase.carrot_auction.entity;

import com.sun.istack.NotNull;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
	private com.zerobase.carrot_auction.model.status status;
	private String title;
	private String siDo;
	private String guGun;
	private int price;
	private String description;
	private int end_period;
	@CreatedDate
	@NotNull
	private LocalDateTime createAt;


}
