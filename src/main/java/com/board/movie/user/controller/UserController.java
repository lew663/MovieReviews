package com.board.movie.user.controller;

import com.board.movie.user.dto.UserDTO;
import com.board.movie.user.entity.UserEntity;
import com.board.movie.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  /**
   * 로그인한 사용자의 정보를 조회
   * @param userId 로그인한 사용자의 아이디
   * @return 사용자의 정보, 응답
   */
  @GetMapping("/{userId}")
  public ResponseEntity<UserEntity> myInfo(@PathVariable String userId) {
    UserEntity userEntity = userService.getUserById(userId);
    return ResponseEntity.ok(userEntity);
  }

  /**
   * 로그인한 사용자의 정보 수정
   * @param userId 로그인한 사용자의 아이디
   * @param user user UserDTO 에 있는 UpdateUser 내부클래스
   * @return 수정된 사용자의 정보, 응답
   */
  @PostMapping("/{userId}")
  public ResponseEntity<UserEntity> updateUser(
      @PathVariable String userId, @RequestBody @Valid UserDTO.UpdateUser user) {
    UserEntity userEntity = userService.updateUser(userId, user);
    return ResponseEntity.ok(userEntity);
  }

  /**
   * 로그인한 사용자의 탈퇴
   * @param userId 로그인한 사용자의 아이디
   * @return 응답
   */
  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
