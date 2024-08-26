package com.board.movie.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScrapResponseDTO {

  private Long postId;
  private String postTitle;
  private String postMovieTitle;
  private String postContent;

}
