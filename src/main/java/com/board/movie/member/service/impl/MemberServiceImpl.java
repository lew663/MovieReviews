package com.board.movie.member.service.impl;

import com.board.movie.member.dto.MemberDTO;
import com.board.movie.member.entity.MemberEntity;
import com.board.movie.member.repository.MemberRepository;
import com.board.movie.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;

  /**
   * 회원가입
   */
  @Override
  public boolean register(MemberDTO memberDTO) {
    // 사용자 존재 여부 확인
    if (memberRepository.existsById(memberDTO.getUserId())) {
      return false; // 사용자가 이미 존재
    }
    MemberEntity memberEntity = MemberEntity.convertToEntity(memberDTO);
    memberRepository.save(memberEntity);
    return true;
  }
}

