package com.board.movie.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponseDTO {
  private String userId;
  private String userName;
  private String role;
  private String message;
}
