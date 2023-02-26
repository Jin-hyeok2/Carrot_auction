package com.zerobase.carrot_auction.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
	NOT_ENTER_TITLE(HttpStatus.BAD_REQUEST, "제목을 입력하지 않았습니다."),
	NOT_ENTER_REGION(HttpStatus.BAD_REQUEST, "지역을 입력하지 않았습니다"),
	NEGATIVE_PRICE(HttpStatus.BAD_REQUEST, "가격이 0이하일 수 없습니다."),
	NEGATIVE_END_PERIOD(HttpStatus.BAD_REQUEST, "기간이 0이하일 수 없습니다."),
	NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "거래글을 찾을 수 없습니다."),
	NOT_FOUND_DEAL(HttpStatus.NOT_FOUND, "거래 정보를 찾을 수 없습니다."),
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
	INVALID_PAGE_INFO(HttpStatus.BAD_REQUEST, "요청 정보 확인이 필요합니다.");
	private final HttpStatus httpStatus;
	private final String detail;
}
