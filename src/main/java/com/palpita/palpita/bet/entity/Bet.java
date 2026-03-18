package com.palpita.palpita.bet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.palpita.palpita.match.entity.Match;
import com.palpita.palpita.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bet {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;

  @ManyToOne
  @JoinColumn(name = "match_id")
  @JsonBackReference
  private Match match;

  private Integer guessA;

  private Integer guessB;

  private Integer points;
}
