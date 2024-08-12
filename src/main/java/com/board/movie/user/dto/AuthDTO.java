package com.board.movie.user.dto;

import com.board.movie.user.domain.entity.UserEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AuthDTO {

  @Data
  public static class SignIn {
    private String userId;
    private String userPassword;
  }

  @Data
  public static class SignUp {

    private String userId;
    private String userPassword;
    private String userNickname;
    private String userName;
    private String userPhone;
    private List<String> roles;

    public UserEntity toEntity() {
      return UserEntity.builder()
          .userId(this.userId)
          .userPassword(this.userPassword)
          .userNickname(this.userNickname)
          .userName(this.userName)
          .userPhone(this.userPhone)
          .roles(this.roles)
          .build();
    }
  }
}
