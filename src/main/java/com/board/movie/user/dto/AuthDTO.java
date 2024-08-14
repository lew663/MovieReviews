package com.board.movie.user.dto;

import com.board.movie.config.security.Authority;
import com.board.movie.user.entity.UserEntity;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AuthDTO {

  @Data
  public static class SignIn {
    @NotBlank(message = "이메일은 Null 또는 공백일 수 없습니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호는 Null 또는 공백일 수 없습니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8~15자 사이여야 합니다.")
    private String userPassword;
  }

  @Data
  public static class SignUp {
    @NotBlank(message = "이메일은 Null 또는 공백일 수 없습니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호는 Null 또는 공백일 수 없습니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8~15자 사이여야 합니다.")
    private String userPassword;

    @NotBlank(message = "닉네임은 Null 또는 공백일 수 없습니다.")
    private String userNickname;

    @NotBlank(message = "이름은 Null 또는 공백일 수 없습니다.")
    private String userName;

    @NotBlank(message = "전화번호는 Null 또는 공백일 수 없습니다.")
    @Pattern(regexp = "^\\d{10,11}$", message = "유효한 전화번호를 입력해주세요 (숫자 10~11자리).")
    private String userPhone;

    public UserEntity toEntity() {
      return UserEntity.builder()
          .userId(this.userId)
          .userPassword(this.userPassword)
          .userNickname(this.userNickname)
          .userName(this.userName)
          .userPhone(this.userPhone)
          .roles(List.of(Authority.ROLE_USER.getRoleName()))
          .build();
    }
  }

  @Data
  public static class PasswordResetRequest {
    @NotBlank(message = "이메일은 Null 또는 공백일 수 없습니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String userId;
  }

  @Data
  public static class PasswordReset {
    @NotBlank
    private String token;

    @NotBlank(message = "비밀번호는 Null 또는 공백일 수 없습니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8~15자 사이여야 합니다.")
    private String newPassword;
  }
}