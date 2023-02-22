package com.zerobase.carrot_auction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchForm extends CommonParam {

	private Boolean auctionYn;
	private String siDo;
	private String guGun;

}
