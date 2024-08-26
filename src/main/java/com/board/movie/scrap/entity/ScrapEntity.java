package com.board.movie.scrap.entity;

import com.board.movie.post.entity.PostEntity;
import com.board.movie.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(ScrapId.class)
@Table(name = "scrap_entity")
public class ScrapEntity {

  @Id
  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "user_Id", nullable = false)
  private UserEntity user;

  @Id
  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "post_id", nullable = false)
  private PostEntity post;
}
