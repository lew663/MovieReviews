package com.board.movie.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender javaMailSender;

  /**
   * 비밀번호 초기화 이메일 발송
   * @param userId 사용자 이메일 주소
   * @param resetToken 비밀번호 초기화 토큰
   */
  public void sendPasswordResetEmail(String userId, String resetToken) {
    SimpleMailMessage email = new SimpleMailMessage();
    String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;
    String subject = "비밀번호 초기화 메일입니다.";
    String text = "링크를 클릭하셔서 비밀번호를 초기화 해주세요\n" + resetLink;

    email.setTo(userId);
    email.setSubject(subject);
    email.setText(text);

    try {
      javaMailSender.send(email);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("이메일 발송에 실패했습니다.");
    }
  }
}
