package com.palpita.palpita.bet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BetRequest {
  private Integer guessA;
  private Integer guessB;
  private Long matchId;
}
