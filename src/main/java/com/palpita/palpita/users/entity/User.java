package com.palpita.palpita.users.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.palpita.palpita.bet.entity.Bet;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Role role;

  private String email;

  private String password;

  private Integer points;

  @OneToMany(mappedBy = "user")
  @JsonManagedReference
  private List<Bet> bets;
}
