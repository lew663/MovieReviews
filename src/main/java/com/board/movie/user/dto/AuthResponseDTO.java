package com.board.movie.user.dto;

import com.board.movie.config.security.UserDetailsImpl;
import com.board.movie.user.entity.Role;
import com.board.movie.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


public class AuthResponseDTO {

  @Data
  @AllArgsConstructor
  public static class LoginResponseDTO {
    private String token;
    private String username; // 필요한 사용자 정보 추가
    private Set<String> roles; // 사용자 권한 추가

    public LoginResponseDTO(String token, UserDetailsImpl userDetails) {
      this.token = token;
      this.username = userDetails.getUsername();
      this.roles = userDetails.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.toSet());
    }
  }

  @Data
  @AllArgsConstructor
  public static class SignUpResponseDTO {
    private String userId;
    private String userName;
    private String role;

    public SignUpResponseDTO(UserEntity user) {
      this.userId = user.getUserId();
      this.userName = user.getUserName();
      this.role = user.getRoles().stream()
          .findFirst()
          .map(Role::getRoleName)
          .orElse("ROLE_USER");
    }
  }
}