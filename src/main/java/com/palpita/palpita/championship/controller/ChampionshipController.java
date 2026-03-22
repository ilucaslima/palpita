package com.palpita.palpita.championship.controller;

import com.palpita.palpita.championship.entity.Championship;
import com.palpita.palpita.championship.service.ChampionshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/championship")
public class ChampionshipController {

    private final ChampionshipService championshipService;

    public ChampionshipController(ChampionshipService championshipService) {
        this.championshipService = championshipService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Championship> save(@RequestBody Championship championship) {
        return ResponseEntity.ok(championshipService.save(championship.getName()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        championshipService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Championship>> findAll() {
        return ResponseEntity.ok(championshipService.findAll());
    }
}
