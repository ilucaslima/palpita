package com.palpita.palpita.championship.repository;

import com.palpita.palpita.championship.entity.Championship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    Optional<Championship> findByName(String name);
}
