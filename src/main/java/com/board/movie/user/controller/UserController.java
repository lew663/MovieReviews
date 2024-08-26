package com.board.movie.user.controller;

import com.board.movie.common.ApiResultDTO;
import com.board.movie.user.dto.UserResponseDTO;
import com.board.movie.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // 로그인 한 사용자 정보 조회
  @GetMapping("/info")
  public ApiResultDTO<UserResponseDTO.UserInfo> getUserInfo() {
    return userService.getUserInfo();
  }
}
