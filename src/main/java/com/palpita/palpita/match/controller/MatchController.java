package com.palpita.palpita.match.controller;

import com.palpita.palpita.match.dto.FinishMatchRequest;
import com.palpita.palpita.match.dto.RegisterMatch;
import com.palpita.palpita.match.entity.Match;
import com.palpita.palpita.match.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/matches")
public class MatchController {
  private final MatchService matchService;

  public MatchController(MatchService matchService) {
    this.matchService = matchService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}/finish")
  public ResponseEntity<Match> finishMatch(
      @PathVariable Long id,
      @RequestBody FinishMatchRequest req
      ){
    Match match = matchService.finishMatch(
        id,
        req.scoreA(),
        req.scoreB()
    );
      return ResponseEntity.ok(match);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/create")
  public ResponseEntity<Match> create(@RequestBody RegisterMatch req) {
    return ResponseEntity.ok(matchService.save(req));
  }
}
