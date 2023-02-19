package com.zerobase.carrot_auction.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

	@ExceptionHandler({ProductException.class})
	public ResponseEntity<ExceptionResponse> customRequestException(
		final ProductException productException) {
		log.warn("api Exception : {}", productException.getErrorCode());
		return ResponseEntity.badRequest()
			.body(new ExceptionResponse(productException.getMessage(),
				productException.getErrorCode()));
	}

	@Getter
	@AllArgsConstructor
	public static class ExceptionResponse {

		private String message;
		private ErrorCode errorCode;
	}
}
