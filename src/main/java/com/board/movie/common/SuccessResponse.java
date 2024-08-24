package com.board.movie.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SuccessResponse {
  private final int status;
  private final String message;

  @Builder
  private SuccessResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public static SuccessResponse of(HttpStatus status, String message) {
    return SuccessResponse.builder()
        .status(status.value())
        .message(message)
        .build();
  }
}