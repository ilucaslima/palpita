package com.palpita.palpita.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
  Long id;
  String email;
  String role;
  String token;
  Integer points;

  public UserResponse(Long id, String email, String role, Integer points, String token){
    this.id = id;
    this.email = email;
    this.role = role;
    this.points = points;
    this.token = token;
  }
}
