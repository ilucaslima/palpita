package com.palpita.palpita.bet.repository;

import com.palpita.palpita.bet.entity.Bet;
import com.palpita.palpita.match.entity.Match;
import com.palpita.palpita.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {
  List<Bet> findByUserId(Long userId);
  List<Bet> findByUserEmail(String email);

  boolean existsByUserAndMatch(User user, Match match);
}
