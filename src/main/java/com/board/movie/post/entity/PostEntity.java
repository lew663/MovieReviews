package com.board.movie.post.entity;

import com.board.movie.comment.entity.CommentEntity;
import com.board.movie.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

  @Column(nullable = false)
  private String postTitle;

  @Column(nullable = false)
  private String postMovieTitle;

  @Column(nullable = false)
  private String postContent;

  @Column(nullable = false)
  private int postViewCount = 0;

  @ManyToOne
  @JoinColumn(nullable = false)
  private UserEntity user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
  private List<CommentEntity> commentList = new ArrayList<>();

  // 게시글 조회수 증가
  public void incrementViewCount() {
    this.postViewCount++;
  }
}
