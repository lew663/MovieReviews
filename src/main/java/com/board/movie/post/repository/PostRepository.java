package com.board.movie.post.repository;

import com.board.movie.post.entity.PostEntity;
import com.board.movie.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
  // 특정 사용자 ID로 게시글 조회
  List<PostEntity> findByUserUserId(String userId);

  // 특정 영화 제목으로 게시글 조회
  List<PostEntity> findByPostMovieTitleContaining(String movieTitle);

  // 게시글 수정시 사용자정보 확인
  Optional<PostEntity> findByPostIdAndUser(Long postId, UserEntity user);
}
