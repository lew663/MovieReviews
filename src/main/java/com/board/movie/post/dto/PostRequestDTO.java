package com.board.movie.post.dto;

import com.board.movie.post.entity.PostEntity;
import com.board.movie.user.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequestDTO {

  @Data
  public static class CreatePost {

    @NotBlank(message = "게시글 제목은 Null 또는 공백일 수 없습니다.")
    @Size(max = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String postTitle;

    @NotBlank(message = "영화 제목은 Null 또는 공백일 수 없습니다.")
    @Size(max = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String postMovieTitle;

    @NotBlank(message = "게시글 내용은 Null 또는 공백일 수 없습니다.")
    @Size(max = 5000, message = "게시글 내용은 최대 5000자까지 입력 가능합니다.")
    private String postContent;

    public PostEntity toEntity(UserEntity user) {
      return PostEntity.builder()
          .postTitle(this.postTitle)
          .postMovieTitle(this.postMovieTitle)
          .postContent(this.postContent)
          .user(user)
          .build();
    }
  }

  @Data
  public static class UpdatePost {
    @NotBlank(message = "게시글 제목은 Null 또는 공백일 수 없습니다.")
    @Size(max = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String postTitle;

    @NotBlank(message = "영화 제목은 Null 또는 공백일 수 없습니다.")
    @Size(max = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String postMovieTitle;

    @NotBlank(message = "게시글 내용은 Null 또는 공백일 수 없습니다.")
    @Size(max = 5000, message = "게시글 내용은 최대 5000자까지 입력 가능합니다.")
    private String postContent;

  }
}
