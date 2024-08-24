package com.board.movie.comment.service;

import com.board.movie.comment.dto.CommentRequestDTO;
import com.board.movie.comment.dto.CommentResponseDTO;
import com.board.movie.comment.entity.CommentEntity;
import com.board.movie.comment.repository.CommentRepository;
import com.board.movie.common.ApiResult;
import com.board.movie.common.ApiResultDTO;
import com.board.movie.common.SuccessResponse;
import com.board.movie.exception.CustomException;
import com.board.movie.exception.ErrorCode;
import com.board.movie.post.entity.PostEntity;
import com.board.movie.post.repository.PostRepository;
import com.board.movie.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  // 댓글 작성
  @Transactional
  public ApiResultDTO<CommentResponseDTO> createComment(Long postId, CommentRequestDTO.CreateComment commentDto, UserEntity user) {

    // 게시글 조회
    PostEntity postEntity = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    CommentEntity comment = new CommentEntity();
    comment.setContent(commentDto.getContent());
    comment.setUser(user);
    comment.setPost(postEntity);

    commentRepository.save(comment);

    CommentResponseDTO commentResponseDTO = new CommentResponseDTO(comment);
    return ApiResult.ok(commentResponseDTO);
  }

  // 댓글 수정
  public ApiResultDTO<CommentResponseDTO> updateComment(Long commentId, CommentRequestDTO.CreateComment commentDto, UserEntity user) {
    // 댓글 조회
    CommentEntity commentEntity = commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 댓글 작성한 유저가 맞는지 확인
    Optional<CommentEntity> comment = commentRepository.findByCommentIdAndUser(commentId, user);
    if (comment.isEmpty()) {
      throw new CustomException(ErrorCode.NOT_WRITER);
    }
    commentEntity.setContent(commentDto.getContent());
    CommentResponseDTO responseDTO = new CommentResponseDTO(commentEntity);
    return ApiResult.ok(responseDTO);
  }

  // 댓글 삭제
  public ApiResultDTO<SuccessResponse> deleteComment(Long commentId, UserEntity user) {
    // 댓글 조회
    CommentEntity commentEntity = commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 댓글 작성한 유저가 맞는지 확인
    Optional<CommentEntity> comment = commentRepository.findByCommentIdAndUser(commentId, user);
    if (comment.isEmpty()) {
      throw new CustomException(ErrorCode.NOT_WRITER);
    }
    commentRepository.delete(commentEntity);
    return ApiResult.ok(SuccessResponse.of(HttpStatus.OK, "댓글 삭제 성공"));
  }
}
