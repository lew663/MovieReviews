package com.board.movie.post.entity;

import com.board.movie.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
