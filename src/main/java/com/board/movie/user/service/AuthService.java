package com.board.movie.user.service;

import com.board.movie.user.dto.AuthDTO;
import com.board.movie.user.entity.UserEntity;
import com.board.movie.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * 회원가입
   * @param user AuthDTO 의 내부클래스(SignUp)
   * @return 가입된 사용자 정보
   * @throws RuntimeException 사용자 아이디가 이미 존재하는 경우 예외 발생
   */
  public UserEntity signUp(AuthDTO.SignUp user) {
    if (userRepository.existsById(user.getUserId())) {
      throw new RuntimeException("User with this ID already exists.");
    }
    String encodedPassword = passwordEncoder.encode(user.getUserPassword());
    UserEntity userEntity = user.toEntity();
    userEntity.setUserPassword(encodedPassword);
    return userRepository.save(userEntity);
  }

  /**
   * 로그인
   * @param user AuthDTO 의 내부클래스(SignIn)
   * @return 인증된 사용자 정보
   * @throws RuntimeException 사용자 아이디가 존재하지 않거나 비밀번호가 일치하지 않는 경우 예외 발생
   */
  public UserEntity signIn(AuthDTO.SignIn user) {
    UserEntity userEntity = userRepository.findByUserId(user.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));
    if (!passwordEncoder.matches(user.getUserPassword(), userEntity.getUserPassword())) {
      throw new RuntimeException("Invalid password");
    }
    return userEntity;
  }
}
