package com.board.movie.post.service;

import com.board.movie.post.dto.PostDTO;
import com.board.movie.post.entity.PostEntity;
import com.board.movie.post.repository.PostRepository;
import com.board.movie.user.entity.UserEntity;
import com.board.movie.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  // 게시글 작성
  @Transactional
  public PostEntity createPost(PostDTO.CreatePost postDto, UserEntity user) {
    PostEntity postEntity = postDto.toEntity(user);
    return postRepository.save(postEntity);
  }

  // 게시글 수정
  @Transactional
  public PostEntity updatePost(Long postId, PostDTO.UpdatePost post, UserEntity user) {

    // 수정할 게시물이 DB에 존재하는지 확인
    Optional<PostEntity> existingPostOpt = postRepository.findByPostIdAndUser(postId, user);
    if (existingPostOpt.isEmpty()) {
      throw new IllegalStateException("게시글이 존재 하지 않습니다.");
    }

    PostEntity existingPost = existingPostOpt.get();

    // 수정할 게시물의 작성자와 토큰에서 가져온 사용자정보 확인
    if (!existingPost.getUser().equals(user)) {
      throw new IllegalStateException("User is not the owner of the post");
    }

    // 게시물 업데이트
    existingPost.setPostTitle(post.getPostTitle());
    existingPost.setPostMovieTitle(post.getPostMovieTitle());
    existingPost.setPostContent(post.getPostContent());

    return postRepository.save(existingPost);
  }

  // 게시글 삭제
  public void deletePost(Long postId) {
    if (postRepository.existsById(postId)) {
      postRepository.deleteById(postId);
    } else {
      throw new RuntimeException("Post not found with id: " + postId);
    }
  }

  // 전체 게시글 조회
  public List<PostEntity> listPosts() {
    return postRepository.findAll();
  }

  // 특정 사용자 ID로 게시글 조회
  public List<PostEntity> getPostsByUserId(String userId) {
    return postRepository.findByUserUserId(userId);
  }

  // 특정 영화 제목으로 게시글 조회
  public List<PostEntity> getPostsByMovieTitle(String movieTitle) {
    return postRepository.findByPostMovieTitleContaining(movieTitle);
  }
}
