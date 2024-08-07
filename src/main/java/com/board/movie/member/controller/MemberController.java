package com.board.movie.member.controller;

import com.board.movie.member.dto.MemberDTO;
import com.board.movie.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/member/register")
  public String register() {
    return "member/register";
  }

  @PostMapping("/member/register")
  public String registerSubmit(@ModelAttribute MemberDTO memberDTO, Model model) {
    boolean result = memberService.register(memberDTO);
    model.addAttribute("result", result);
    return "member/register_complete";
  }
}
