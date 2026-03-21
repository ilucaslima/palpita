package com.palpita.palpita.bet.service;

import com.palpita.palpita.bet.dto.BetRequest;
import com.palpita.palpita.bet.dto.BetResponse;
import com.palpita.palpita.bet.entity.Bet;
import com.palpita.palpita.bet.repository.BetRepository;
import com.palpita.palpita.match.dto.RegisterMatch;
import com.palpita.palpita.match.entity.Match;
import com.palpita.palpita.match.entity.StatusMatch;
import com.palpita.palpita.match.repository.MatchRepository;
import com.palpita.palpita.users.entity.User;
import com.palpita.palpita.users.repository.UserRepository;
import com.palpita.palpita.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BetService {
  private final BetRepository betRepository;
  private final MatchRepository matchRepository;
  private final UserService userService;
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;

  public BetService(
      BetRepository betRepository,
      MatchRepository matchRepository,
      UserRepository userRepository,
      UserService userService, AuthenticationManager authenticationManager){
    this.betRepository = betRepository;
    this.matchRepository = matchRepository;
    this.userService = userService;
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
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

  public Bet update(Long id, BetRequest req) {
    Authentication auth = SecurityContextHolder.getContext()
        .getAuthentication();

    String email = auth.getName();

    Bet bet = betRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bet not found"));

    Match match = bet.getMatch();

    if (!bet.getUser().getEmail().equals(email)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this bet");
    }

    if (match.getStatusMatch() != StatusMatch.SCHEDULED ||
        match.getDate().isBefore(LocalDateTime.now())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update bet: Match has already started or finished");
    }

    if (req.getGuessA() != null) {
      bet.setGuessA(req.getGuessA());
    }
    if (req.getGuessB() != null) {
      bet.setGuessB(req.getGuessB());
    }

    return betRepository.save(bet);
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
