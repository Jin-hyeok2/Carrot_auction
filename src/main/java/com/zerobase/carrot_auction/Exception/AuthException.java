package com.zerobase.carrot_auction.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException {

	private final ErrorCode errorCode;

	@Getter
	@RequiredArgsConstructor
	public enum ErrorCode {
		NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
		ALREADY_USING_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
		ALREADY_USING_NICKNAME(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
		ALREADY_USING_PHONE(HttpStatus.CONFLICT, "이미 등록된 전화번호입니다."),
		NOT_CORRECT_AUTH_CODE(HttpStatus.UNAUTHORIZED, "이메일 인증코드가 맞지않습니다."),
		UNAUTHORIZED_EMAIL(HttpStatus.UNAUTHORIZED, "이메일 인증이 완료되지 않았습니다."),
		NOT_CORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
		NOT_NEW_PASSWORD(HttpStatus.CONFLICT, "변경 비밀번호가 이전 비밀번호와 같습니다."),
		NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 정보입니다.");
		private final HttpStatus httpStatus;
		private final String detail;

	}

	public AuthException(ErrorCode errorCode) {
		super(errorCode.getDetail());
		this.errorCode = errorCode;
	}
}
