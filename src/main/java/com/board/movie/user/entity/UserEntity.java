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

  @Column(nullable = false)
  private String userPassword;

  @Column(nullable = false)
  private String userNickname;

  @Column(nullable = false)
  private String userName;

  @Column(nullable = false)
  private String userPhone;

  @ManyToMany
  private Set<Role> roles;

}

