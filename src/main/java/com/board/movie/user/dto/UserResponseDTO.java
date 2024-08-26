package com.board.movie.user.dto;

import com.board.movie.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDTO {

  @Getter
  @Setter
  public static class UserInfo {

    private String userId;
    private String userNickname;
    private String userName;
    private String userPhone;

    public UserInfo(UserEntity userEntity) {
      this.userId = userEntity.getUserId();
      this.userNickname = userEntity.getUserNickname();
      this.userName = userEntity.getUserName();
      this.userPhone = userEntity.getUserPhone();
    }
  }
}
