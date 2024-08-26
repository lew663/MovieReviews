package com.board.movie.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {

  @Column(updatable = false, nullable = false)
  private LocalDateTime postCreated;

  @Column(updatable = false, nullable = false)
  private LocalDateTime postUpdated;

  // 게시글 생성시 postCreated 와 postUpdated 를 현재시각으로 변경 Null 값 방지
  @PrePersist
  public void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    if (postCreated == null) {
      postCreated = now;
    }
    postUpdated = now;
  }

  @PreUpdate
  public void preUpdate() {
    postUpdated = LocalDateTime.now();
  }
}
