package com.board.movie.post.service;

import com.board.movie.common.ApiResult;
import com.board.movie.common.ApiResultDTO;
import com.board.movie.common.SuccessResponse;
import com.board.movie.exception.CustomException;
import com.board.movie.exception.ErrorCode;
import com.board.movie.post.dto.PostDTO;
import com.board.movie.post.dto.PostResponseDTO;
import com.board.movie.post.entity.PostEntity;
import com.board.movie.post.repository.PostRepository;
import com.board.movie.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  // 전체 게시글 조회
  @Transactional
  public ApiResultDTO<List<PostResponseDTO>> listPosts() {
      List<PostEntity> postList = postRepository.findAllByOrderByPostUpdatedDesc();
      if (postList == null || postList.isEmpty()) {
        return ApiResult.ok(Collections.emptyList());
      }
      List<PostResponseDTO> postResponseDTOList = new ArrayList<>();
      for (PostEntity postEntity : postList) {
        PostResponseDTO postResponseDTO = new PostResponseDTO(postEntity);
        postResponseDTOList.add(postResponseDTO);
      }
      return ApiResult.ok(postResponseDTOList);
  }

  // 게시글 작성
  @Transactional
  public ApiResultDTO<PostResponseDTO> createPost(PostDTO.CreatePost postDto, UserEntity user) {
    try {
      // 게시글 엔티티 생성
      PostEntity postEntity = postDto.toEntity(user);

      // 게시글 저장
      PostEntity savedPostEntity = postRepository.save(postEntity);

      // PostResponseDTO 생성
      PostResponseDTO postResponseDTO = new PostResponseDTO(
          savedPostEntity.getPostId(),
          savedPostEntity.getPostTitle(),
          savedPostEntity.getPostMovieTitle(),
          savedPostEntity.getPostContent(),
          savedPostEntity.getUser().getUserId(),
          savedPostEntity.getPostCreated(),
          savedPostEntity.getPostUpdated()
      );

      // 성공 응답 반환
      return ApiResult.ok(postResponseDTO);
    } catch (Exception e) {
      log.error("게시글 작성중 오류 발생: " + e);
      throw new CustomException(ErrorCode.INVALID_REQUEST_DATA);
    }
  }
  // 게시글 수정
  @Transactional
  public ApiResultDTO<PostResponseDTO> updatePost(Long postId, PostDTO.UpdatePost postDto, UserEntity user) {

    // 게시글 조회
    PostEntity postEntity = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 게시글을 작성한 유저가 맞는지 확인
    Optional<PostEntity> post = postRepository.findByPostIdAndUser(postId, user);
    if (post.isEmpty()) {
      throw new CustomException(ErrorCode.NOT_WRITER);
    }

    // 게시글 정보 업데이트
    postEntity.setPostTitle(postDto.getPostTitle());
    postEntity.setPostMovieTitle(postDto.getPostMovieTitle());
    postEntity.setPostContent(postDto.getPostContent());

    PostResponseDTO responseDTO = new PostResponseDTO(postEntity);

    return ApiResult.ok(responseDTO);
  }

  // 게시글 삭제
  public ApiResultDTO<SuccessResponse> deletePost(Long postId, UserEntity user) {

    // 게시글 조회
    PostEntity postEntity = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 작성자 확인
    Optional<PostEntity> post = postRepository.findByPostIdAndUser(postId, user);
    if (post.isEmpty()) {
      throw new CustomException(ErrorCode.NOT_WRITER);
    }

    postRepository.delete(postEntity);
    return ApiResult.ok(SuccessResponse.of(HttpStatus.OK, "게시물 삭제 성공"));
  }
}
