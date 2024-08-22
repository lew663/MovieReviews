package com.board.movie.post.entity;

import com.board.movie.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "post_entity")
public class PostEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;

  private String postTitle;
  private String postMovieTitle;
  private String postContent;

  @ManyToOne
  @JoinColumn(nullable = false)
  private UserEntity user;
}
