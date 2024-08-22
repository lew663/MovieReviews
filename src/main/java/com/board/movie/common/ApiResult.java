package com.board.movie.common;

public class ApiResult {

  // 요청 성공인 경우
  public static <T> ApiResultDTO<T> ok(T response) {
    return ApiResultDTO.<T>builder()
        .success(true)
        .response(response)
        .build();
  }

  // 에러 발생한 경우
  public static <T> ApiResultDTO<T> error(ErrorResponse response) {
    return ApiResultDTO.<T>builder()
        .success(false)
        .error(response)
        .build();
  }

}
