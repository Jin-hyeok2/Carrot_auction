package com.zerobase.carrot_auction.Exception;

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
	NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다.");
	private final HttpStatus httpStatus;
	private final String detail;
}
