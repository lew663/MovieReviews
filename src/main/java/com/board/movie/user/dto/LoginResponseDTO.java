package com.board.movie.user.dto;

import com.board.movie.config.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
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
