package com.zerobase.carrot_auction.repository.entity.code;


public enum Status {

	SALE("판매 중"),
	BIDDING("입찰 중"),
	CLOSE("거래 종료"),
	DEL("삭제됨");
	final String value;

	Status(String value) {
		this.value = value;
	}
}
