package com.board.movie.user.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "user_entity")
public class UserEntity extends BaseEntity {

  @Id
  private String userId;

  private String userPassword;
  private String userNickname;
  private String userName;
  private String userPhone;
  private String resetToken;

  @ElementCollection
  private List<String> roles;
}

