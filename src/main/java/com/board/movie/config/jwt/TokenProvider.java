package com.board.movie.config.jwt;

import com.board.movie.exception.CustomException;
import com.board.movie.exception.ErrorCode;
import com.board.movie.user.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

  private final UserDetailsService userDetailsService;

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String AUTHORIZATION_KEY = "auth";
  private static final String BEARER_PREFIX = "Bearer ";
  private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
  private static final String KEY_ROLES = "role";

  private final Key key;

  @Autowired
  public TokenProvider(@Value("${spring.jwt.secret}") String secretKey, UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  // header 토큰을 가져오기
  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  // 토큰 생성
  public String generateToken(String userId, Set<Role> roles) {

    List<String> roleNames = roles.stream()
        .map(Role::getRoleName)
        .map(roleName -> roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName)
        .collect(Collectors.toList());

    Date date = new Date();

    return Jwts.builder()
        .setSubject(userId)
        .claim(AUTHORIZATION_KEY, roleNames)
        .setExpiration(new Date(date.getTime() + TOKEN_EXPIRE_TIME))
        .setIssuedAt(date)
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();
  }

  // 인증 객체 생성
  public Authentication getAuthentication(String userId) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  // JWT 토큰 검증
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token, 만료된 JWT token 입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
    }
    return false;
  }

  // 클레임 파싱
  public Claims parseClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (MalformedJwtException e) {
      log.debug("JWT 형식 오류: {}", e.getMessage(), e);
      throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
    } catch (JwtException e) {
      log.error("JWT 클레임 파싱 오류: {}", e.getMessage(), e);
      throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
    }
  }

  // JWT 에서 사용자 ID 추출
  public Claims getUserId(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }
}
