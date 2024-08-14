package com.board.movie.user.repository;

import com.board.movie.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

  Optional<UserEntity> findByUserId(String userId);
  Optional<UserEntity> findByResetToken(String resetToken);
}
