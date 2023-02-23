package com.zerobase.carrot_auction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonParam {

	long pageNum;
	long pageSize;
	private String searchWord;
}
