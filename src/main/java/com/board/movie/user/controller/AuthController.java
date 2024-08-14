package com.board.movie.user.controller;

import com.board.movie.config.security.TokenProvider;
import com.board.movie.user.dto.AuthDTO;
import com.board.movie.user.dto.AuthResponseDTO;
import com.board.movie.user.entity.UserEntity;
import com.board.movie.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;
  private final TokenProvider tokenProvider;

  /**
   * 회원가입
   *
   * @param user AuthDTO 의 내부클래스(SignUp)
   * @return 사용자의 정보, 응답
   */
  @PostMapping("/signup")
  public ResponseEntity<UserEntity> signUp(
      @Valid @RequestBody AuthDTO.SignUp user) {
    try {
      UserEntity userEntity = authService.signUp(user);
      return ResponseEntity.ok(userEntity);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  /**
   * 사용자 로그인
   *
   * @param user AuthDTO 의 내부클래스(SignIn)
   * @return JWT 토큰과 사용자의 정보(AuthResponseDTO), 응답
   */
  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@RequestBody AuthDTO.SignIn user) {
    try {
      UserEntity userEntity = authService.signIn(user);
      String token = tokenProvider.generateToken(userEntity.getUserId(), userEntity.getRoles());
      AuthResponseDTO response = new AuthResponseDTO(token, userEntity);
      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }

  /**
   * 비밀번호 재설정 요청
   *
   * @param request AuthDTO 의 내부클래스(PasswordResetRequest)
   * @return 응답
   */
  @PostMapping("/reset-password-request")
  public ResponseEntity<?> requestPasswordReset(@RequestBody AuthDTO.PasswordResetRequest request) {
    try {
      authService.requestPasswordReset(request.getUserId());
      return ResponseEntity.ok("비밀번호 재설정 이메일이 발송되었습니다.");
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body("이메일 발송에 실패했습니다.");
    }
  }

  /**
   * 비밀번호 재설정
   *
   * @param request AuthDTO 의 내부클래스(PasswordReset)
   * @return 응답
   */
  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestBody AuthDTO.PasswordReset request) {
    try {
      authService.resetPassword(request.getToken(), request.getNewPassword());
      return ResponseEntity.ok("비밀번호가 성공적으로 재설정되었습니다.");
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body("비밀번호 재설정에 실패했습니다.");
    }
  }

}
