package com.palpita.palpita.match.repository;

import com.palpita.palpita.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MatchRepository extends JpaRepository<Match, Long> {

  @Query("select case when count(m) > 0 then true else false end from Match m " +
      "where m.date = :date and " +
      "((lower(m.teamA) = lower(:teamA) and lower(m.teamB) = lower(:teamB)) " +
      "or (lower(m.teamA) = lower(:teamB) and lower(m.teamB) = lower(:teamA)))")
  boolean existsByTeamsAndDate(
      @Param("teamA") String teamA,
      @Param("teamB") String teamB,
      @Param("date") LocalDateTime date
  );
}
