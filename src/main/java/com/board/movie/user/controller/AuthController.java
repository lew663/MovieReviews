package com.board.movie.user.controller;

import com.board.movie.user.config.security.TokenProvider;
import com.board.movie.user.domain.entity.UserEntity;
import com.board.movie.user.dto.AuthDTO;
import com.board.movie.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;
  private final TokenProvider tokenProvider;

  @PostMapping("/signup")
  public ResponseEntity<UserEntity> signUp(@RequestBody AuthDTO.SignUp user) {
    try {
      UserEntity userEntity = authService.signUp(user);
      return ResponseEntity.ok(userEntity);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@RequestBody AuthDTO.SignIn user) {
    try {
      UserEntity userEntity = authService.signIn(user);
      String token = tokenProvider.generateToken(userEntity.getUserId(), userEntity.getRoles());
      return ResponseEntity.ok(Map.of("token", token, "user", userEntity));
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }
}
