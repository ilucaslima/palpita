package com.palpita.palpita.match.scheduler;

import com.palpita.palpita.match.entity.Match;
import com.palpita.palpita.match.entity.StatusMatch;
import com.palpita.palpita.match.repository.MatchRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MatchStatusScheduler {

  private final MatchRepository matchRepository;

  public MatchStatusScheduler(MatchRepository matchRepository) {
    this.matchRepository = matchRepository;
  }

  @Scheduled(cron = "0 */5 * * * *")
  public void checkMatchStartTime() {
    LocalDateTime now = LocalDateTime.now();

    List<Match> startedMatches = matchRepository
        .findByStatusMatchAndDateBefore(StatusMatch.SCHEDULED, now);

    if (!startedMatches.isEmpty()) {
      startedMatches.forEach(match -> match.setStatusMatch(StatusMatch.LIVE));
      matchRepository.saveAll(startedMatches);
    }
  }

  @Scheduled(cron = "0 */5 * * * *")
  public void finishCompletedMatches() {
    LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(2);

    List<Match> matchesToFinish = matchRepository
        .findByStatusMatchAndDateBefore(StatusMatch.LIVE, twoHoursAgo);

    if (!matchesToFinish.isEmpty()) {
      matchesToFinish.forEach(match -> match.setStatusMatch(StatusMatch.FINISHED));
      matchRepository.saveAll(matchesToFinish);
    }
  }
}