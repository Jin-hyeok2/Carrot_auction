package com.zerobase.carrot_auction.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {

	Long sellerId;
	@NotBlank(message = "제목을 입력해주세요.")
	private String title;
	private boolean auctionYn;
	@NotBlank(message = "시/도를 입력해주세요.")
	private String siDo;
	@NotBlank(message = "구/군을 입력해주세요.")
	private String guGun;
	@PositiveOrZero(message = "가격이 음수일 수 없습니다.")
	private int price;
	private String description;
	@PositiveOrZero(message = "마감기간이 음수일 수 없습니다.")
	private int endPeriod;
}
