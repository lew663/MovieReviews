package com.board.movie.user.service;

import com.board.movie.common.ApiResult;
import com.board.movie.common.ApiResultDTO;
import com.board.movie.exception.CustomException;
import com.board.movie.exception.ErrorCode;
import com.board.movie.user.dto.UserResponseDTO;
import com.board.movie.user.entity.UserEntity;
import com.board.movie.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public ApiResultDTO<UserResponseDTO.UserInfo> getUserInfo() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
      String username = ((UserDetails) authentication.getPrincipal()).getUsername();

      UserEntity userEntity = userRepository.findById(username)
          .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

      UserResponseDTO.UserInfo userInfo = new UserResponseDTO.UserInfo(userEntity);
      return ApiResult.ok(userInfo);
    } else {
      throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
    }
  }
}
