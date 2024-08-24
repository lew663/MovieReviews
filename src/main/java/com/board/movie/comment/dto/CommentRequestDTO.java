package com.board.movie.comment.dto;

import lombok.Data;
import lombok.Setter;

@Data
public class CommentRequestDTO {

  @Setter
  @Data
  public static class CreateComment {
    private String content;
  }
}
