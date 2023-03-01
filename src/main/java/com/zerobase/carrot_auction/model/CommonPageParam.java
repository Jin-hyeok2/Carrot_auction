package com.zerobase.carrot_auction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonPageParam {
	private long pageNum =1;
	private long pageSize = 10;
}
