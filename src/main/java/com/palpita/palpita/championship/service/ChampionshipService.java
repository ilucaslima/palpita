package com.palpita.palpita.championship.service;

import com.palpita.palpita.championship.entity.Championship;
import com.palpita.palpita.championship.repository.ChampionshipRepository;
import com.palpita.palpita.users.entity.User;
import com.palpita.palpita.users.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChampionshipService {
    private final ChampionshipRepository championshipRepository;
    private final UserRepository userRepository;

    public ChampionshipService(
            ChampionshipRepository championshipRepository,
            UserRepository userRepository
    ) {
        this.championshipRepository = championshipRepository;
        this.userRepository = userRepository;
    }

    public Championship save(String name) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Championship> existingChampionship = championshipRepository.findByName(name);
        if (existingChampionship.isPresent()) {
            throw new RuntimeException("Championship with this name already exists");
        }

        Championship championship = new Championship();

        championship.setName(name);
        championship.setSlug(name.toLowerCase().replace(" ", "-"));
        championship.setUser(user);

        return championshipRepository.save(championship);
    }

    public void delete(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Championship championship = championshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Championship not found"));

        if (!championship.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only delete championships created by you");
        }

        championshipRepository.delete(championship);
    }

    public List<Championship> findAll() {
        return championshipRepository.findAll();
    }
}
