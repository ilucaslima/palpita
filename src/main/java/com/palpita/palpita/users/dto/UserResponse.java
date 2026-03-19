package com.palpita.palpita.users.dto;

import lombok.AllArgsConstructor;
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

  public UserResponse(Long id, String email, String role, Integer points){
    this.id = id;
    this.email = email;
    this.role = role;
    this.points = points;
  }
}
