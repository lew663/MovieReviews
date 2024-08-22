package com.board.movie.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "user_entity")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

  @Id
  private String userId;

  private String userPassword;
  private String userNickname;
  private String userName;
  private String userPhone;
  private String resetToken;

  @ManyToMany
  private Set<Role> roles;

}

