package com.board.movie.config.jwt;

import io.jsonwebtoken.Claims;
import com.board.movie.exception.CustomException;
import com.board.movie.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final TokenProvider tokenProvider;

  public static final String TOKEN_HEADER = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String token = resolveTokenFromRequest(request);

    // 토큰이 null 이거나 유효성 검사 실패시
    if (token == null || !tokenProvider.validateToken(token)) {
      log.warn("Invalid or missing token: {}", token);
      filterChain.doFilter(request, response);
      return;
    }

    // 토큰이 유효하다면 토큰으로부터 사용자 정보를 가져온다.
    Claims info = tokenProvider.parseClaims(token);
    try {
      setAuthentication(info.getSubject());   // 사용자 정보로 인증 객체 만들기
    } catch (UsernameNotFoundException e) {
      log.error("JWT parsing error: {}", e.getMessage(), e);
      throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
    } catch (Exception e) {
      log.error("Unexpected JWT processing error: {}", e.getMessage(), e);
      throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
    }
    filterChain.doFilter(request, response);
  }

  private String resolveTokenFromRequest(HttpServletRequest request) {
    String token = request.getHeader(TOKEN_HEADER);
    if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
      return token.substring(TOKEN_PREFIX.length());
    }
    return null;
  }

  // 인증 객체 생성
  private void setAuthentication(String userId) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Authentication authentication = tokenProvider.getAuthentication(userId); // 인증 객체 만들기
    if (authentication != null) {
      context.setAuthentication(authentication);
      SecurityContextHolder.setContext(context);
    }
  }
}
