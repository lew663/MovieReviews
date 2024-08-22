package com.board.movie.post.controller;

import com.board.movie.config.security.CustomUserDetails;
import com.board.movie.post.dto.PostDTO;
import com.board.movie.post.entity.PostEntity;
import com.board.movie.post.service.PostService;
import com.board.movie.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

  private final PostService postService;

  // 게시글 작성
  @PostMapping("/post")
  public ResponseEntity<PostEntity> createPost(
      @RequestBody PostDTO.CreatePost postDto,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    UserEntity userEntity = userDetails.getUser();
    PostEntity postEntity = postService.createPost(postDto, userEntity);
    return ResponseEntity.ok(postEntity);
  }

  // 게시글 수정
  @PutMapping("/post/{postId}")
  public ResponseEntity<PostEntity> updatePost(
      @PathVariable Long postId,
      @RequestBody PostDTO.UpdatePost post,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    PostEntity postEntity = postService.updatePost(postId, post, userDetails.getUser());
    return ResponseEntity.ok(postEntity);
  }

  // 게시글 삭제
  @DeleteMapping("/post/{postId}")
  public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
    postService.deletePost(postId);
    return ResponseEntity.noContent().build();
  }

  // 전체 게시글 조회
  @GetMapping("/posts")
  public ResponseEntity<List<PostEntity>> listPosts() {
    List<PostEntity> postEntities = postService.listPosts();
    return ResponseEntity.ok(postEntities);
  }

  // userId로 게시글 조회
  @GetMapping("/users/{userId}")
  public ResponseEntity<List<PostEntity>> getPostByUserId(@PathVariable String userId) {
    List<PostEntity> postEntities = postService.getPostsByUserId(userId);
    return ResponseEntity.ok(postEntities);
  }

  // 영화 제목으로 게시글 조회
  @GetMapping("/movies/{movieTitle}")
  public ResponseEntity<List<PostEntity>> getPostByMovieId(@PathVariable String movieTitle) {
    List<PostEntity> postEntities = postService.getPostsByMovieTitle(movieTitle);
    return ResponseEntity.ok(postEntities);
  }

}
