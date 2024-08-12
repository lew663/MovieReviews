package com.board.movie.config.security;

public enum Authority {
  ROLE_USER,
  ROLE_ADMIN;

  /**
   * enum 상수를 문자열로 반환
   */
  public String getRoleName() {
    return this.name();
  }

  /**
   * 문자열을 enum 상수로 반환
   * 문자열이 유효하지 않을시 에러발생
   */
  public static Authority fromRoleName(String roleName) {
    for (Authority authority : values()) {
      if (authority.name().equals(roleName)) {
        return authority;
      }
    }
    throw new IllegalStateException("Invalid role name " + roleName);
  }
}