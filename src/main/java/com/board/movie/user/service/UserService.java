package com.board.movie.user.service;

import com.board.movie.common.ApiResult;
import com.board.movie.common.ApiResultDTO;
import com.board.movie.common.SuccessResponse;
import com.board.movie.exception.CustomException;
import com.board.movie.exception.ErrorCode;
import com.board.movie.user.dto.UserRequestDTO;
import com.board.movie.user.dto.UserResponseDTO;
import com.board.movie.user.entity.UserEntity;
import com.board.movie.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  // 로그인 한 회원 정보 조회
  @Transactional
  public ApiResultDTO<UserResponseDTO> getUserInfo() {

    // 현재 인증된 사용자 정보 가져오기
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    UserEntity userEntity = userRepository.findById(username)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    UserResponseDTO userInfo = new UserResponseDTO(userEntity);
    return ApiResult.ok(userInfo);
  }

  // 회원 정보 수정
  @Transactional
  public ApiResultDTO<UserResponseDTO> updateUser(UserRequestDTO.UpdateUser userDTO) {

    // 현재 인증된 사용자 정보 가져오기
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    UserEntity userEntity = userRepository.findById(username)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    userEntity.setUserNickname(userDTO.getUserNickname());
    userEntity.setUserPhone(userDTO.getUserPhone());

    UserResponseDTO userInfo = new UserResponseDTO(userEntity);
    return ApiResult.ok(userInfo);
  }

  public ApiResultDTO<SuccessResponse> deleteUser() {

    // 현재 인증된 사용자 정보 가져오기
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    if (!userRepository.existsById(username)) {
      throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }

    userRepository.deleteById(username);
    return ApiResult.ok(SuccessResponse.of(HttpStatus.OK, "회원 탈퇴 성공"));
  }
}
