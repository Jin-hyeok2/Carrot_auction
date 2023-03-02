package com.zerobase.carrot_auction.exception;

import lombok.Getter;

@Getter
public class DealException extends RuntimeException {

	private final ErrorCode errorCode;

	public DealException(ErrorCode errorCode) {
		super(errorCode.getDetail());
		this.errorCode = errorCode;
	}
}
