package com.board.movie.config.jwt;

import com.board.movie.config.security.CustomUserDetailsService;
import com.board.movie.exception.CustomException;
import com.board.movie.exception.ErrorCode;
import com.board.movie.user.entity.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.SignatureException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

  private final CustomUserDetailsService customUserDetailsService;

  private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
  private static final String KEY_ROLES = "role";

  @Value("${spring.jwt.secret}")
  private String secretKey;

  // 토큰 생성
  public String generateToken(String userId, Set<Role> roleEntities) {

    Claims claims = Jwts.claims().setSubject(userId);
    List<String> roleNames = roleEntities.stream()
        .map(Role::getRoleName)
        .map(roleName -> roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName)
        .collect(Collectors.toList());
    claims.put(KEY_ROLES, roleNames);

    var now = new Date();
    var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expiredDate)
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  // 인증 객체 생성
  public Authentication getAuthentication(String token) {
    String userId = getUserId(token);
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
    List<String> roles = getRoles(token);

    if (userDetails == null) {
      throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
    }

    Collection<SimpleGrantedAuthority> authorities = roles == null ?
        List.of() :
        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(
        userDetails, "", authorities);
  }

  // JWT 토큰 검증
  public boolean validateToken(String token) {
    if (!StringUtils.hasText(token)) {
      return false;
    }
    try {
      Claims claims = parseClaims(token);
      return !claims.getExpiration().before(new Date());
    } catch (ExpiredJwtException e) {
      log.warn("JWT 토큰이 만료되었습니다: {}", e.getMessage());
      throw new CustomException(ErrorCode.EXPIRED_AUTH_TOKEN);
    } catch (MalformedJwtException e) {
      log.error("JWT 형식 오류: {}", e.getMessage(), e);
      throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
    } catch (JwtException e) {
      log.error("JWT 검증 오류: {}", e.getMessage(), e);
      throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
    }
  }

  // 클레임 파싱
  public Claims parseClaims(String token) {
    try {
      return Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token)
          .getBody();
    } catch (MalformedJwtException e) {
      log.error("JWT 형식 오류: {}", e.getMessage(), e);
      throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
    } catch (JwtException e) {
      log.error("JWT 클레임 파싱 오류: {}", e.getMessage(), e);
      throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
    }
  }

  // JWT 에서 사용자 ID 추출
  public String getUserId(String token) {
    return parseClaims(token).getSubject();
  }

  // JWT 에서 권한 추출
  private List<String> getRoles(String token) {
    Claims claims = parseClaims(token);
    return claims.get(KEY_ROLES, List.class);
  }
}
