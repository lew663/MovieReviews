package com.board.movie.user.controller;

import com.board.movie.common.ApiResultDTO;
import com.board.movie.user.dto.AuthRequestDTO;
import com.board.movie.user.dto.AuthResponseDTO;
import com.board.movie.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/auth/signup")
  public ApiResultDTO<AuthResponseDTO.SignUpResponseDTO> signUp(
      @Valid @RequestBody AuthRequestDTO.SignUp signUpRequest) {
      return authService.signUp(signUpRequest);
  }

  @PostMapping("/auth/login")
  public ApiResultDTO<AuthResponseDTO.LoginResponseDTO> login(@Valid @RequestBody AuthRequestDTO.Login loginRequest) {
    return authService.login(loginRequest);
  }
}
