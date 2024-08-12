package com.board.movie.user.service;

import com.board.movie.user.dto.UserDTO;
import com.board.movie.user.entity.UserEntity;
import com.board.movie.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  /**
   * 사용자 ID로 사용자를 조회
   * @param userId 사용자 아이디(이메일)
   * @return 조회된 사용자 Entity
   * @throws RuntimeException Id가 존재하지 않을때 예외 발생
   */
  public UserEntity getUserById(String userId) {
    return userRepository.findByUserId(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

  /**
   * 사용자 정보 수정
   * @param userId 사용자 아이디(이메일)
   * @param user UserDTO 에 있는 UpdateUser 내부클래스
   * @return 수정된 사용자 정보
   */
  public UserEntity updateUser(String userId, UserDTO.UpdateUser user) {
    UserEntity userEntity = getUserById(userId);
    userEntity.setUserNickname(user.getUserNickname());
    userEntity.setUserPhone(user.getUserPhone());
    return userRepository.save(userEntity);
  }

  /**
   * 사용자 삭제(탈퇴)
   * @param userId 사용자 아이디(이메일)
   */
  public void deleteUser(String userId) {
    UserEntity userEntity = getUserById(userId);
    userRepository.delete(userEntity);
  }
}
