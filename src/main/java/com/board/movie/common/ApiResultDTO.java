package com.board.movie.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResultDTO<T> {

  private final boolean success;
  private final T response;
  private final ErrorResponse error;

  @Builder
  private ApiResultDTO(boolean success, T response, ErrorResponse error) {
    this.success = success;
    this.response = response;
    this.error = error;
  }
}
