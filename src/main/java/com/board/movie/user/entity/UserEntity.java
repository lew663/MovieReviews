package com.board.movie.user.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserEntity extends BaseEntity {

  @Id
  private String userId;

  private String userPassword;
  private String userNickname;
  private String userName;
  private String userPhone;

  @ElementCollection
  private List<String> roles;

}

