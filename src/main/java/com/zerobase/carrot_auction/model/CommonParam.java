package com.zerobase.carrot_auction.model;

import lombok.Data;

@Data
public class CommonParam {

	long pageIndex;
	long pageSize;
	private String searchWord;

	public long getPageStart() {
		init();
		return (pageIndex - 1) * pageSize;
	}

	public long getPageEnd() {
		init();
		return pageSize;
	}

	public void init() {
		if (pageIndex < 1) {
			pageIndex = 1;
		}

		if (pageSize < 10) {
			pageSize = 10;
		}
	}
}
