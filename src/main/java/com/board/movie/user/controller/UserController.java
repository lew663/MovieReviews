package com.board.movie.user.controller;

import com.board.movie.common.ApiResultDTO;
import com.board.movie.user.dto.UserRequestDTO;
import com.board.movie.user.dto.UserResponseDTO;
import com.board.movie.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // 로그인 한 사용자 정보 조회
  @GetMapping("/info")
  public ApiResultDTO<UserResponseDTO> getUserInfo() {
    return userService.getUserInfo();
  }

  // 로그인 된 사용자 정보 수정
  @PutMapping("/update")
  public ApiResultDTO<UserResponseDTO> updateUser(@Valid @RequestBody UserRequestDTO.UpdateUser userDTO) {
    return userService.updateUser(userDTO);
  }
}
