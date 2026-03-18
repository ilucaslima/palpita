package com.palpita.palpita.bet.dto;

import com.palpita.palpita.bet.entity.Bet;
import com.palpita.palpita.match.entity.Match;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class BetResponse {
  private Long id;
  private String email;
  private int guessA;
  private int guessB;
  private Integer points;

  private Long matchId;
  private String teamA;
  private String teamB;

  public BetResponse(Bet bet) {
    this.id = bet.getId();
    this.email = bet.getUser().getEmail();
    this.guessA = bet.getGuessA();
    this.guessB = bet.getGuessB();
    this.points = bet.getPoints();

    this.matchId = bet.getMatch().getId();
    this.teamA = bet.getMatch().getTeamA();
    this.teamB = bet.getMatch().getTeamB();
  }
}
