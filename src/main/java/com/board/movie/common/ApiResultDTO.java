package com.board.movie.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResultDTO<T> {

  private boolean success;
  private T response;
  private ErrorResponse error;

  @Builder
  private ApiResultDTO(boolean success, T response, ErrorResponse error) {
    this.success = success;
    this.response = response;
    this.error = error;
  }
}
