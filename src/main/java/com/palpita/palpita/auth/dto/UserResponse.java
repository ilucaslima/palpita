package com.palpita.palpita.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
  Long id;
  String email;
  String role;
  Integer points;
  List<?> bets;

  public UserResponse(Long id, String email, String role, Integer points, List<?> bets){
    this.id = id;
    this.email = email;
    this.role = role;
    this.points = points;
    this.bets = bets;
  }
}
