package com.board.movie.user.controller;

import com.board.movie.common.ApiResultDTO;
import com.board.movie.common.SuccessResponse;
import com.board.movie.user.dto.AuthDTO;
import com.board.movie.user.dto.LoginResponseDTO;
import com.board.movie.user.dto.SignUpResponseDTO;
import com.board.movie.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ApiResultDTO<SignUpResponseDTO> signUp(
      @Valid @RequestBody AuthDTO.SignUp signUpRequest) {
      return authService.signUp(signUpRequest);
  }

  @PostMapping("/login")
  public ApiResultDTO<LoginResponseDTO> login(@Valid @RequestBody AuthDTO.Login loginRequest) {
    return authService.login(loginRequest);
  }

  @PostMapping("/reset-password-request")
  public ResponseEntity<?> requestPasswordReset(@Valid @RequestBody AuthDTO.PasswordResetRequest request) {
    authService.requestPasswordReset(request.getUserId());
    return ResponseEntity.ok("비밀번호 재설정 이메일이 발송되었습니다.");
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@Valid @RequestBody AuthDTO.PasswordReset request) {
    authService.resetPassword(request.getToken(), request.getNewPassword());
    return ResponseEntity.ok("비밀번호가 성공적으로 재설정되었습니다.");
  }
}
