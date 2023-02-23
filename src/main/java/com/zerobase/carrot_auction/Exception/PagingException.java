package com.zerobase.carrot_auction.Exception;

import lombok.Getter;

@Getter
public class PagingException extends RuntimeException {

	private final ErrorCode errorCode;

	public PagingException(ErrorCode errorCode) {
		super(errorCode.getDetail());
		this.errorCode = errorCode;
	}
}
