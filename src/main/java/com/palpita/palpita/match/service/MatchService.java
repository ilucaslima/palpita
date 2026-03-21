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
import java.util.List;

@Service
public class MatchService {
  private final MatchRepository matchRepository;

  public MatchService(MatchRepository matchRepository){
    this.matchRepository = matchRepository;
  }

  public Match getMatchById(Long id){
    return matchRepository.findById(id).orElseThrow(() -> new RuntimeException("Match not found"));
  }

  public List<Match> getAllMatches(){
    return matchRepository.findAll();
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

  public Match update(Long id, RegisterMatch req) {
    Match match = matchRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));

    if (match.getStatusMatch() == StatusMatch.FINISHED) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update a finished match");
    }

    if (req.getTeamA() != null) match.setTeamA(req.getTeamA());
    if (req.getTeamB() != null) match.setTeamB(req.getTeamB());
    if (req.getDate() != null) {
      if (req.getDate().isBefore(LocalDateTime.now())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game date has passed");
      }
      match.setDate(req.getDate());
    }

    boolean conflict = matchRepository.existsByTeamAAndTeamBAndDateAndIdNot(
        match.getTeamA(),
        match.getTeamB(),
        match.getDate(),
        id
    );

    if (conflict) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Another match already exists with these teams at this date/time");
    }

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

    boolean conflict = matchRepository.existsByTeamsAndDate(req.getTeamA(), req.getTeamB(), req.getDate());

    if (conflict) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match already exists at this date/time");
    }

    match.setScoreA(0);
    match.setScoreB(0);
    match.setStatusMatch(StatusMatch.SCHEDULED);

    return matchRepository.save(match);
  }
}
