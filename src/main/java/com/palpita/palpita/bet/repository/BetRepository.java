package com.palpita.palpita.bet.repository;

import com.palpita.palpita.bet.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {
  List<Bet> findByUserId(Long userId);
  List<Bet> findByUserEmail(String email);
}
