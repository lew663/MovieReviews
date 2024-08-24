package com.board.movie.post.dto;

import com.board.movie.post.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponseDTO {
  private Long postId;
  private String postTitle;
  private String postMovieTitle;
  private String postContents;
  private String userId;
  private LocalDateTime createdDt;
  private LocalDateTime updatedDt;

  public PostResponseDTO(PostEntity postEntity) {
    this.postId = postEntity.getPostId();
    this.postTitle = postEntity.getPostTitle();
    this.postMovieTitle = postEntity.getPostMovieTitle();
    this.postContents = postEntity.getPostContent();
    this.userId = postEntity.getUser().getUserId();
    this.createdDt = postEntity.getPostCreated();
    this.updatedDt = postEntity.getPostUpdated();
  }

}
