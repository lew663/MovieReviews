package com.board.movie.comment.entity;

import com.board.movie.post.entity.PostEntity;
import com.board.movie.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId;

  @Column(nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = false)
  private PostEntity post;


}
