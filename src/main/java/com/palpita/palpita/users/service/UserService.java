package com.palpita.palpita.users.service;

import com.palpita.palpita.auth.dto.UserResponse;
import com.palpita.palpita.bet.entity.Bet;
import com.palpita.palpita.bet.repository.BetRepository;
import com.palpita.palpita.security.JwtService;
import com.palpita.palpita.users.entity.Role;
import com.palpita.palpita.users.entity.User;
import com.palpita.palpita.users.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final BetRepository betRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public UserService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      BetRepository betRepository,
      JwtService jwtService
  ){
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.betRepository = betRepository;
  }

  public Optional<User> findByEmail(String email){
    return userRepository.findByEmail(email);
  }

  public UserResponse save(User user) {

    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new RuntimeException("Error: Email já cadastrado!!!");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(Role.USER);
    user.setPoints(0);

    userRepository.save(user);

    String token = jwtService.generateToken(user.getEmail());

    return new UserResponse(
        user.getId(),
        user.getEmail(),
        user.getRole().name(),
        user.getPoints(),
        user.getBets(),
        token
    );
  }

  public User makeAdmin(Long userId){
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    user.setRole(Role.ADMIN);

    return userRepository.save(user);
  }
}
