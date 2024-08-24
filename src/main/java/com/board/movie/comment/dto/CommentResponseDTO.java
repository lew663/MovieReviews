package com.board.movie.comment.dto;

import com.board.movie.comment.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
  private Long commentId;
  private String content;
  private String userId;
  private LocalDateTime commentCreated;
  private LocalDateTime commentUpdated;

  // entity -> DTO
  public CommentResponseDTO(CommentEntity commentEntity) {
    this.commentId = commentEntity.getCommentId();
    this.content = commentEntity.getContent();
    this.userId = commentEntity.getUser().getUserId();
    this.commentCreated = commentEntity.getCommentCreated();
    this.commentUpdated = commentEntity.getCommentUpdated();
  }
}
