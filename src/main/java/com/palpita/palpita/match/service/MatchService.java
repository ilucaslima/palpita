package com.palpita.palpita.match.service;

import com.palpita.palpita.match.dto.RegisterMatch;
import com.palpita.palpita.match.entity.Match;
import com.palpita.palpita.match.entity.StatusMatch;
import com.palpita.palpita.match.repository.MatchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class MatchService {
  private final MatchRepository matchRepository;

  public MatchService(MatchRepository matchRepository){
    this.matchRepository = matchRepository;
  }

  public Match finishMatch(Long matchId, Integer scoreA, Integer scoreB){
    Match match = matchRepository.findById(matchId).orElseThrow(() -> new RuntimeException("Match not found"));

    if(match.getStatusMatch() == StatusMatch.FINISHED){
      throw new RuntimeException("Match already finished");
    }

    match.setScoreA(scoreA);
    match.setScoreB(scoreB);
    match.setStatusMatch(StatusMatch.FINISHED);

    return matchRepository.save(match);
  }

  public Match save(RegisterMatch req) {
    Match match = new Match();

    match.setTeamA(req.getTeamA());
    match.setTeamB(req.getTeamB());
    match.setDate(req.getDate());

    if(req.getDate().isBefore(LocalDateTime.now())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game date has passed");
    }

    match.setScoreA(0);
    match.setScoreB(0);
    match.setStatusMatch(StatusMatch.SCHEDULED);

    return matchRepository.save(match);
  }
}
