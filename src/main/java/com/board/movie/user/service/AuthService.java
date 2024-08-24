package com.board.movie.user.service;

import com.board.movie.common.ApiResult;
import com.board.movie.common.ApiResultDTO;
import com.board.movie.config.jwt.TokenProvider;
import com.board.movie.config.security.UserDetailsImpl;
import com.board.movie.email.service.EmailService;
import com.board.movie.exception.CustomException;
import com.board.movie.exception.ErrorCode;
import com.board.movie.user.dto.AuthDTO;
import com.board.movie.user.dto.LoginResponseDTO;
import com.board.movie.user.dto.SignUpResponseDTO;
import com.board.movie.user.entity.Role;
import com.board.movie.user.entity.UserEntity;
import com.board.movie.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;
  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;

  // 회원가입
  @Transactional
  public ApiResultDTO<SignUpResponseDTO> signUp(AuthDTO.SignUp signUpRequest) {

    // 회원 중복 확인
    if (userRepository.existsById(signUpRequest.getUserId())) {
      throw new CustomException(ErrorCode.DUPLICATE_USERID);
    }

    // 비밀번호 암호화
    String encodedPassword = passwordEncoder.encode(signUpRequest.getUserPassword());

    UserEntity userEntity = signUpRequest.toEntity();
    userEntity.setUserPassword(encodedPassword);

    // 기본 사용자 역할(Role) 설정
    Role userRole = new Role();
    userRole.setRoleName("ROLE_USER");
    userEntity.setRoles(Set.of(userRole));

    // 사용자 정보 저장
    UserEntity savedUserEntity = userRepository.save(userEntity);

    // 성공 응답 반환
    SignUpResponseDTO responseDTO = new SignUpResponseDTO(
        savedUserEntity.getUserId(),
        savedUserEntity.getUserName(),
        savedUserEntity.getRoles().stream()
            .findFirst()
            .map(Role::getRoleName)
            .orElse("ROLE_USER"),
        "회원가입 성공"
    );
    return ApiResult.ok(responseDTO);
  }

  // 로그인
  public ApiResultDTO<LoginResponseDTO> login(AuthDTO.Login loginRequest) {
    // 사용자 인증 정보 생성
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getUserPassword());

    try {
      Authentication authentication = authenticationManager.authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();

      // 사용자 권한(Role) 추출
      Set<Role> roleEntities = userDetails.getAuthorities().stream()
          .map(authority -> new Role(authority.getAuthority()))
          .collect(Collectors.toSet());

      // JWT 토큰 생성
      String token = tokenProvider.generateToken(userDetails.getUsername(), roleEntities);

      // LoginResponseDTO 객체 생성
      LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token, userDetails.getUsername(), roleEntities.stream()
          .map(Role::getRoleName)
          .collect(Collectors.toSet()));

      return ApiResult.ok(loginResponseDTO);
    } catch (BadCredentialsException e) {
      throw new CustomException(ErrorCode.NOT_MATCHING_INFO);
    }
  }

  /**
   * 비밀번호 재설정 요청
   *
   * @param userId 사용자 ID
   * @throws RuntimeException 사용자 ID가 존재하지 않으면 예외 발생
   */
  @Transactional
  public void requestPasswordReset(String userId) {
    // 사용자 조회
    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    // 리셋 토큰 생성 및 저장
    String resetToken = UUID.randomUUID().toString();
    user.setResetToken(resetToken);
    userRepository.save(user);

    // 이메일 발송
    emailService.sendPasswordResetEmail(userId, resetToken);
  }

  /**
   * 비밀번호 재설정
   *
   * @param token       비밀번호 재설정 토큰
   * @param newPassword 새로운 비밀번호
   * @throws RuntimeException 유효하지 않은 토큰이거나, 사용자를 찾을 수 없으면 예외 발생
   */
  @Transactional
  public void resetPassword(String token, String newPassword) {
    // 사용자 조회
    UserEntity user = userRepository.findByResetToken(token)
        .orElseThrow(() -> new CustomException(ErrorCode.INVALID_AUTH_TOKEN));

    // 비밀번호 암호화 및 저장
    user.setUserPassword(passwordEncoder.encode(newPassword));
    user.setResetToken(null); // 사용 후 토큰 제거
    userRepository.save(user);
  }

}
