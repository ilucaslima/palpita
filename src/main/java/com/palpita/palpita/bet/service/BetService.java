package com.palpita.palpita.bet.service;

import com.palpita.palpita.bet.dto.BetRequest;
import com.palpita.palpita.bet.dto.BetResponse;
import com.palpita.palpita.bet.entity.Bet;
import com.palpita.palpita.bet.repository.BetRepository;
import com.palpita.palpita.match.entity.Match;
import com.palpita.palpita.match.repository.MatchRepository;
import com.palpita.palpita.users.entity.User;
import com.palpita.palpita.users.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetService {
  private final BetRepository betRepository;
  private final MatchRepository matchRepository;
  private final UserService userService;

  public BetService(
      BetRepository betRepository,
      MatchRepository matchRepository,
      UserService userService){
    this.betRepository = betRepository;
    this.matchRepository = matchRepository;
    this.userService = userService;
  }

  public List<BetResponse> listAllWithDetails(){
    return betRepository.findAll().stream()
        .map(BetResponse::new)
        .toList();
  }

  public List<BetResponse> listBetsByUserWithDetails(){
    Authentication auth = SecurityContextHolder.getContext()
        .getAuthentication();

    String email = auth.getName();

    return betRepository.findByUserEmail(email).stream()
        .map(BetResponse::new)
        .toList();
  }

  public BetResponse save(BetRequest bet){
    Bet betUser = new Bet();

    Authentication auth = SecurityContextHolder.getContext()
        .getAuthentication();

    String email = auth.getName();

    User user = userService.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

    Match match = matchRepository.findById(bet.getMatchId())
        .orElseThrow(() -> new RuntimeException("Match not found"));

    boolean alreadyExists = betRepository.existsByUserAndMatch(user, match);

    if (alreadyExists) {
      throw new RuntimeException("Você já fez um palpite para essa partida");
    }

    betUser.setGuessA(bet.getGuessA());
    betUser.setGuessB(bet.getGuessB());
    betUser.setMatch(match);
    betUser.setUser(user);

    betRepository.save(betUser);

    return new BetResponse(betUser);
  }
}
