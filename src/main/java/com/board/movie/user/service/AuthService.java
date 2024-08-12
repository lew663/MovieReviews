package com.board.movie.user.service;

import com.board.movie.user.domain.entity.UserEntity;
import com.board.movie.user.dto.AuthDTO;
import com.board.movie.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;


  public UserEntity signUp(AuthDTO.SignUp user) {
    if(userRepository.existsById(user.getUserId())) {
      throw new RuntimeException("User with this ID already exists.");
    }
    String encodedPassword = passwordEncoder.encode(user.getUserPassword());
    UserEntity userEntity = user.toEntity();
    userEntity.setUserPassword(encodedPassword);

    return userRepository.save(userEntity);
  }

  public UserEntity signIn(AuthDTO.SignIn user) {
    UserEntity userEntity = userRepository.findByUserId(user.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(user.getUserPassword(), userEntity.getUserPassword())) {
      throw new RuntimeException("Invalid password");
    }
    return userEntity;
  }

}
