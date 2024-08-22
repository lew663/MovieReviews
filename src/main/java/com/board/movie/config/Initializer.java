package com.board.movie.config;

import com.board.movie.user.entity.Role;
import com.board.movie.user.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Initializer {

  private final RoleRepository roleRepository;

  @PostConstruct
  public void init() {

    // ROLE_USER 이 데이터에 존재하지 않으면 생성
    Optional<Role> userRole = roleRepository.findById("ROLE_USER");
    if (userRole.isEmpty()) {
      roleRepository.save(new Role("ROLE_USER"));
    }
  }
}
