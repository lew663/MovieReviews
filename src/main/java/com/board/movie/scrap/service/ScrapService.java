package com.board.movie.scrap.service;

import com.board.movie.common.ApiResult;
import com.board.movie.common.ApiResultDTO;
import com.board.movie.common.SuccessResponse;
import com.board.movie.exception.CustomException;
import com.board.movie.exception.ErrorCode;
import com.board.movie.post.entity.PostEntity;
import com.board.movie.post.repository.PostRepository;
import com.board.movie.scrap.dto.ScrapResponseDTO;
import com.board.movie.scrap.entity.ScrapEntity;
import com.board.movie.scrap.repository.ScrapRepository;
import com.board.movie.user.entity.UserEntity;
import com.board.movie.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapService {

  private final ScrapRepository scrapRepository;
  private final UserRepository userRepository;
  private final PostRepository postRepository;

  // 스크랩 조회
  public ApiResultDTO<List<ScrapResponseDTO>> listScraps() {

    // 현재 인증된 사용자의 정보를 가져옴
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    UserEntity user = userRepository.findByUserId(username)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    // 해당 사용자의 스크랩한 게시물 가져옴
    List<ScrapEntity> scraps = scrapRepository.findByUser(user);

    List<ScrapResponseDTO> responseDTOs = scraps.stream()
        .map(scrap -> {
          ScrapResponseDTO dto = new ScrapResponseDTO();
          dto.setPostId(scrap.getPost().getPostId());
          dto.setPostTitle(scrap.getPost().getPostTitle());
          dto.setPostMovieTitle(scrap.getPost().getPostMovieTitle());
          dto.setPostContent(scrap.getPost().getPostContent());
          return dto;
        }).toList();

    return ApiResult.ok(responseDTOs);
  }

  // 스크랩 생성
  public ApiResultDTO<SuccessResponse> createScrap(Long postId) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    UserEntity user = userRepository.findByUserId(username)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    PostEntity post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    ScrapEntity scrap = new ScrapEntity(user, post);
    scrapRepository.save(scrap);

    return ApiResult.ok(SuccessResponse.of(HttpStatus.OK, "스크랩 성공"));
  }

  // 스크랩 삭제
  public ApiResultDTO<SuccessResponse> deleteScrap(Long postId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    UserEntity user = userRepository.findByUserId(username)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    PostEntity post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    ScrapEntity scrap = scrapRepository.findByUserAndPost(user, post)
        .orElseThrow(() -> new CustomException(ErrorCode.SCRAP_NOT_FOUND));

    scrapRepository.delete(scrap);

    return ApiResult.ok(SuccessResponse.of(HttpStatus.OK, "스크랩 삭제 성공"));
  }
}
