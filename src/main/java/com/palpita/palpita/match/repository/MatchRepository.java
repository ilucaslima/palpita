package com.palpita.palpita.match.repository;

import com.palpita.palpita.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
