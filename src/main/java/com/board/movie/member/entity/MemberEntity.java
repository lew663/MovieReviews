package com.board.movie.member.entity;

import com.board.movie.member.dto.MemberDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity extends BaseEntity{

  @Id
  private String userId;

  private String userPassword;
  private String userNickname;
  private String userName;
  private String userPhone;

  /**
   * DTO -> Entity
   */
  public static MemberEntity convertToEntity(MemberDTO memberDTO) {
    return MemberEntity.builder()
        .userId(memberDTO.getUserId())
        .userPassword(memberDTO.getUserPassword())
        .userNickname(memberDTO.getUserNickname())
        .userName(memberDTO.getUserName())
        .userPhone(memberDTO.getUserPhone())
        .build();
  }
}
