package com.board.movie.user.config.security;

import com.board.movie.user.domain.entity.UserEntity;
import com.board.movie.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByUserId(userId)
        .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + userId));
    return new CustomUserDetails(userEntity);
  }
}
