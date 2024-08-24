package com.board.movie.config.jwt;

import io.jsonwebtoken.Claims;
import com.board.movie.exception.CustomException;
import com.board.movie.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {

    String token = tokenProvider.resolveToken(request);

    // 토큰이 null이거나 유효성 검사 실패 시
    if (token == null || !tokenProvider.validateToken(token)) {
      log.warn("유효하지 않거나 누락된 토큰: {}", token);
      filterChain.doFilter(request, response); // 다음 필터로 요청을 전달
      return;
    }

    // 토큰이 유효하다면, 토큰으로부터 사용자 정보를 가져온다
    Claims info = tokenProvider.getUserId(token);
    try {
      setAuthentication(info.getSubject());   // 사용자 정보로 인증 객체 만들기
    } catch (UsernameNotFoundException e) {
      log.error("JWT 파싱 오류: {}", e.getMessage(), e);
      throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN); // 사용자 정보를 찾지 못할 경우 예외 발생
    } catch (Exception e) {
      log.error("예상치 못한 JWT 처리 오류: {}", e.getMessage(), e);
      throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN); // 다른 예외 발생 시에도 예외 처리
    }

    filterChain.doFilter(request, response); // 다음 필터로 요청을 전달
  }


  // 인증 객체 생성 및 SecurityContext에 설정
  private void setAuthentication(String userId) {
    SecurityContext context = SecurityContextHolder.createEmptyContext(); // 빈 SecurityContext 생성
    Authentication authentication = tokenProvider.getAuthentication(userId); // 사용자 정보로 인증 객체 생성
    if (authentication != null) {
      context.setAuthentication(authentication); // 인증 객체를 SecurityContext에 설정
      SecurityContextHolder.setContext(context); // SecurityContext를 현재 스레드에 설정
    }
  }

}

