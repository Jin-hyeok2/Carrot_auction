package com.zerobase.carrot_auction.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "거래글을 찾을 수 없습니다."),
    NOT_FOUND_DEAL(HttpStatus.NOT_FOUND, "거래 정보를 찾을 수 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    INVALID_USER(HttpStatus.BAD_REQUEST, "판매자 정보가 일치하지 않습니다."),
    INVALID_PRICE(HttpStatus.BAD_REQUEST, "입력한 가격이 입찰가보다 낮습니다"),
    INVALID_PRODUCT_STATUS(HttpStatus.BAD_REQUEST, "판매중인 거래만 종료할 수 있습니다."),
    INVALID_PAGE_INFO(HttpStatus.BAD_REQUEST, "요청 정보 확인이 필요합니다."),
    TRANSACTION_NOT_COMPLETED(HttpStatus.BAD_REQUEST, "거래가 종료된 상품만 삭제할 수 있습니다."),
    DELETE_PRODUCT(HttpStatus.BAD_REQUEST, "삭제된 거래글입니다.");
    private final HttpStatus httpStatus;
    private final String detail;
}
