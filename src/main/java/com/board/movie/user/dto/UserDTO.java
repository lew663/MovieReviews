package com.board.movie.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {

  @Data
  public static class UpdateUser{

    @NotBlank(message = "닉네임은 Null 또는 공백일 수 없습니다.")
    private String userNickname;

    @NotBlank(message = "전화번호는 Null 또는 공백일 수 없습니다.")
    @Pattern(regexp = "^\\d{10,11}$", message = "유효한 전화번호를 입력해주세요 (숫자 10~11자리).")
    private String userPhone;
  }

}
