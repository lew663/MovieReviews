package com.board.movie.member.dto;

import lombok.*;

@Data
@ToString
public class MemberDTO {

  private String userId;
  private String userPassword;
  private String userNickname;
  private String userName;
  private String userPhone;

}
