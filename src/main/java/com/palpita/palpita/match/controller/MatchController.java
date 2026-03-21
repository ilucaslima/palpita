package com.palpita.palpita.match.controller;

import com.palpita.palpita.match.dto.FinishMatchRequest;
import com.palpita.palpita.match.dto.RegisterMatch;
import com.palpita.palpita.match.entity.Match;
import com.palpita.palpita.match.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/matches")
public class MatchController {
  private final MatchService matchService;

  public MatchController(MatchService matchService) {
    this.matchService = matchService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Match> getMatchById(@PathVariable Long id){
    return ResponseEntity.ok(matchService.getMatchById(id));
  }

  @GetMapping()
  public ResponseEntity<List<Match>> getAllMatches(){
    return ResponseEntity.ok(matchService.getAllMatches());
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

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}/update")
  public ResponseEntity<Match> update( @PathVariable Long id, @RequestBody RegisterMatch req){
    return ResponseEntity.ok(matchService.update(id, req));
  }
}
