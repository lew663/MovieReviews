package com.board.movie.comment.repository;

import com.board.movie.comment.entity.CommentEntity;
import com.board.movie.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
  Optional<CommentEntity> findByCommentIdAndUser(Long commentId, UserEntity user);
}
