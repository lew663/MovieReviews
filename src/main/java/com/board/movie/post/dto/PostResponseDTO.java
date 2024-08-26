package com.board.movie.post.dto;

import com.board.movie.comment.entity.CommentEntity;
import com.board.movie.post.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class listPosts {
    private Long postId;
    private String postTitle;
    private String postMovieTitle;
    private String userId;
    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;

    public listPosts(PostEntity postEntity) {
      this.postId = postEntity.getPostId();
      this.postTitle = postEntity.getPostTitle();
      this.postMovieTitle = postEntity.getPostMovieTitle();
      this.userId = postEntity.getUser().getUserId();
      this.createdDt = postEntity.getPostCreated();
      this.updatedDt = postEntity.getPostUpdated();
    }
  }

  private Long postId;
  private String postTitle;
  private String postMovieTitle;
  private String postContents;
  private String userId;
  private int postViewCount;
  private List<CommentEntity> commentList;
  private LocalDateTime createdDt;
  private LocalDateTime updatedDt;

  public PostResponseDTO(PostEntity postEntity) {
    this.postId = postEntity.getPostId();
    this.postTitle = postEntity.getPostTitle();
    this.postMovieTitle = postEntity.getPostMovieTitle();
    this.postContents = postEntity.getPostContent();
    this.userId = postEntity.getUser().getUserId();
    this.postViewCount = postEntity.getPostViewCount();
    this.commentList = postEntity.getCommentList();
    this.createdDt = postEntity.getPostCreated();
    this.updatedDt = postEntity.getPostUpdated();
  }
}
