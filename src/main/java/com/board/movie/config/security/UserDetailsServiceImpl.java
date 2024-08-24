package com.board.movie.config.security;

import com.board.movie.exception.CustomException;
import com.board.movie.exception.ErrorCode;
import com.board.movie.user.entity.UserEntity;
import com.board.movie.user.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByUserId(userId)
        .orElseThrow(() ->  new CustomException(ErrorCode.USER_NOT_FOUND)); // 사용자가 DB에 없으면 예외발생

    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getRoleName())) // 역할 이름을 SimpleGrantedAuthority로 변환
        .collect(Collectors.toList());

    return new UserDetailsImpl(user);
  }
}
