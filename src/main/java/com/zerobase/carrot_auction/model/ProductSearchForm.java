package com.zerobase.carrot_auction.model;

import com.zerobase.carrot_auction.repository.entity.code.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearchForm extends CommonPageParam {
	private String searchWord;
	private Boolean auctionYn;
	private String siDo;
	private String guGun;
	private Long productId;
	final private Status status = Status.DEL;

}
