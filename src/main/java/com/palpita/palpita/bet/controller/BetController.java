package com.palpita.palpita.bet.controller;

import com.palpita.palpita.bet.dto.BetRequest;
import com.palpita.palpita.bet.dto.BetResponse;
import com.palpita.palpita.bet.service.BetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bets")
public class BetController {
  private final BetService betService;

  public BetController(BetService betService) {
    this.betService = betService;
  }

  @GetMapping("/")
  public ResponseEntity<List<BetResponse>> listAll(){
    return ResponseEntity.ok(betService.listAllWithDetails());
  }

  @PostMapping("/create")
  public ResponseEntity<BetResponse> save(
      @RequestBody BetRequest req
  ){
    return ResponseEntity.ok(betService.save(req));
  }

  @GetMapping("/myBets")
  public ResponseEntity<List<BetResponse>> listBets(){
    return ResponseEntity.ok(betService.listBetsByUserWithDetails());
  }
}
