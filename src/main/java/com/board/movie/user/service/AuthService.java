package com.board.movie.user.service;

import com.board.movie.common.ApiResult;
import com.board.movie.common.ApiResultDTO;
import com.board.movie.config.jwt.TokenProvider;
import com.board.movie.exception.CustomException;
import com.board.movie.exception.ErrorCode;
import com.board.movie.user.dto.AuthRequestDTO;
import com.board.movie.user.dto.AuthResponseDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;

  // 회원가입
  @Transactional
  public ApiResultDTO<AuthResponseDTO.SignUpResponseDTO> signUp(AuthRequestDTO.SignUp signUpRequest) {

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

    AuthResponseDTO.SignUpResponseDTO responseDTO = new AuthResponseDTO.SignUpResponseDTO(savedUserEntity);
    return ApiResult.ok(responseDTO);
  }

  // 로그인
  public ApiResultDTO<AuthResponseDTO.LoginResponseDTO> login(AuthRequestDTO.Login loginRequest) {
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
      AuthResponseDTO.LoginResponseDTO loginResponseDTO = new AuthResponseDTO.LoginResponseDTO(token, userDetails.getUsername(), roleEntities.stream()
          .map(Role::getRoleName)
          .collect(Collectors.toSet()));

      return ApiResult.ok(loginResponseDTO);
    } catch (BadCredentialsException e) {
      throw new CustomException(ErrorCode.NOT_MATCHING_INFO);
    }
  }
}
