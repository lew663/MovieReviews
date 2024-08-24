package com.board.movie.post.controller;

import com.board.movie.common.ApiResultDTO;
import com.board.movie.common.SuccessResponse;
import com.board.movie.config.security.UserDetailsImpl;
import com.board.movie.post.dto.PostDTO;
import com.board.movie.post.dto.PostResponseDTO;
import com.board.movie.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

  private final PostService postService;

  // 전체 게시글 조회
  @GetMapping("/posts")
  public ApiResultDTO<List<PostResponseDTO>> listPosts() {
    return postService.listPosts();
  }

  // 게시글 작성
  @PostMapping("/post")
  public ApiResultDTO<PostResponseDTO> createPost(
      @Valid @RequestBody PostDTO.CreatePost postDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return postService.createPost(postDto, userDetails.getUser());
  }

  // 게시글 수정
  @PutMapping("/post/{postId}")
  public ApiResultDTO<PostResponseDTO> updatePost(
      @PathVariable("postId") Long postId,
      @Valid @RequestBody PostDTO.UpdatePost postDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return postService.updatePost(postId, postDto, userDetails.getUser());
  }

  // 게시글 삭제
  @DeleteMapping("/post/{postId}")
  public ApiResultDTO<SuccessResponse> deletePost(
      @PathVariable("postId") Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return postService.deletePost(postId, userDetails.getUser());
  }

}
