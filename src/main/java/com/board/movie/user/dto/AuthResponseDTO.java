package com.board.movie.user.dto;

import com.board.movie.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
  private String token;
  private UserEntity user;
}
