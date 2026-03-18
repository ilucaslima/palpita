package com.palpita.palpita.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
  Long id;
  String email;
  String role;
  String token;
  Integer points;
  List bets;

  public UserResponse(Long id, String email, String role, Integer points, List bets, String token){
    this.id = id;
    this.email = email;
    this.role = role;
    this.points = points;
    this.bets = bets;
    this.token = token;
  }
}
