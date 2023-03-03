package com.zerobase.carrot_auction.exception;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({ProductException.class})
    public ResponseEntity<ExceptionResponse> productException(
            final ProductException productException) {
        log.warn("api Exception : {}", productException.getErrorCode());
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(productException.getErrorCode().getDetail(),
                        productException.getErrorCode()));
    }

    @ExceptionHandler({AuthException.class})
    public ResponseEntity<ExceptionRes<AuthException.ErrorCode>> authException(
            final AuthException authException) {
        return ResponseEntity.status(authException.getErrorCode().getHttpStatus())
                .body(new ExceptionRes<>(authException.getMessage(),
                        authException.getErrorCode()));
    }

    @ExceptionHandler({JwtException.class})
    public ResponseEntity<?> jwtExceptionHandler(
            final JwtException jwtException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionRes<>(jwtException.getMessage(), jwtException));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> processValidationError(
            MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);

        return ResponseEntity.badRequest()
                .body(new ValidationErrorResponse(fieldError.getDefaultMessage(),
                        fieldError.getCode()));
    }

    @Getter
    @AllArgsConstructor
    public static class ValidationErrorResponse {

        private String message;
        private String errorCode;
    }

    @Getter
    @AllArgsConstructor
    public static class ExceptionResponse {

        private String message;
        private ErrorCode errorCode;
    }

    @Getter
    @AllArgsConstructor
    public static class ExceptionRes<T> {

        private String message;
        private T errorCode;
    }
}
