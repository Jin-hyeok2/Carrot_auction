package com.zerobase.carrot_auction.Exception;

import lombok.Getter;

@Getter
public class ProductException extends RuntimeException {

	private final ErrorCode errorCode;

	public ProductException(ErrorCode errorCode) {
		super(errorCode.getDetail());
		this.errorCode = errorCode;
	}
}
