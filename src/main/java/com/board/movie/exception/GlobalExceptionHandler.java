package com.board.movie.exception;

import com.board.movie.common.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  // Hibernate 관련된 에러 처리
  @ExceptionHandler(value = { ConstraintViolationException.class })
  protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
    log.error("제약 조건 위반 예외 발생: {}", ex.getMessage(), ex);
    return ErrorResponse.toResponseEntity(ErrorCode.INVALID_REQUEST_DATA);
  }

  @ExceptionHandler(value = { DataIntegrityViolationException.class })
  protected ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    log.error("데이터 무결성 예외 발생: {}", ex.getMessage(), ex);
    return ErrorResponse.toResponseEntity(ErrorCode.DUPLICATE_USERID);
  }

  // CustomException 처리
  @ExceptionHandler(value = { CustomException.class })
  protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
    log.error("커스텀 예외 발생, 코드: {}, 메시지: {}", e.getErrorCode(), e.getMessage(), e);
    return ErrorResponse.toResponseEntity(e.getErrorCode());
  }
}