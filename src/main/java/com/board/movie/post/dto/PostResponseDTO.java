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
    this.postId = postEntity.getPostId(); // 엔티티의 ID 필드에서 postId로 매핑
    this.postTitle = postEntity.getPostTitle(); // 엔티티의 title 필드에서 postTitle로 매핑
    this.postMovieTitle = postEntity.getPostMovieTitle(); // 엔티티의 movieTitle 필드에서 postMovieTitle로 매핑
    this.postContents = postEntity.getPostContent(); // 엔티티의 contents 필드에서 postContents로 매핑
    this.userId = postEntity.getUser().getUserId(); // 엔티티의 userId 필드에서 userId로 매핑
    this.createdDt = postEntity.getPostCreated(); // 엔티티의 createdAt 필드에서 createdDt로 매핑
    this.updatedDt = postEntity.getPostUpdated(); // 엔티티의 updatedAt 필드에서 updatedDt로 매핑
  }

}
