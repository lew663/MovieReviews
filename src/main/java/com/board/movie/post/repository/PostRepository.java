package com.board.movie.post.repository;

import com.board.movie.post.entity.PostEntity;
import com.board.movie.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

  // 전체 게시글 조회 (내림차순)
  List<PostEntity> findAllByOrderByPostUpdatedDesc(); // postEntity의 게시물 수정 시간을 기준으로 내림차순

  Optional<PostEntity> findByPostIdAndUser(Long postId, UserEntity user);
}
