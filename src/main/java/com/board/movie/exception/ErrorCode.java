package com.board.movie.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  INVALID_REQUEST_DATA(BAD_REQUEST, "요청 데이터가 유효하지 않습니다"),
  INVALID_AUTH_TOKEN(UNAUTHORIZED, "유효하지 않은 인증 토큰입니다."), // 비밀번호 재설정
  NOT_MATCHING_INFO(UNAUTHORIZED, "회원을 찾을 수 없습니다."), // 로그인
  UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
  EXPIRED_AUTH_TOKEN(UNAUTHORIZED, "인증 토큰이 만료되었습니다"),
  USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다"),
  MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"), // 비밀번호 재설정 요청
  DUPLICATE_USERID(CONFLICT, "데이터가 이미 존재합니다"); // 회원가입

  private final HttpStatus httpStatus;
  private final String detail;
}