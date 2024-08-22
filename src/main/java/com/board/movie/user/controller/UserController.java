package com.board.movie.user.controller;

import com.board.movie.user.dto.UserDTO;
import com.board.movie.user.entity.UserEntity;
import com.board.movie.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/{userId}")
  public ResponseEntity<UserEntity> myInfo(@PathVariable String userId) {
    UserEntity userEntity = userService.getUserById(userId);
    return ResponseEntity.ok(userEntity);
  }

  @PostMapping("/{userId}")
  public ResponseEntity<UserEntity> updateUser(
      @PathVariable String userId, @RequestBody @Valid UserDTO.UpdateUser user) {
    UserEntity userEntity = userService.updateUser(userId, user);
    return ResponseEntity.ok(userEntity);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
