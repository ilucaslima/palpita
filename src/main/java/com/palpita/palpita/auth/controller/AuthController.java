package com.palpita.palpita.auth.controller;

import com.palpita.palpita.auth.dto.RegisterRequest;
import com.palpita.palpita.auth.dto.RegisterResponse;
import com.palpita.palpita.users.entity.Role;
import com.palpita.palpita.users.entity.User;
import com.palpita.palpita.users.repository.UserRepository;
import com.palpita.palpita.security.JwtService;

import com.palpita.palpita.users.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  public AuthController(
      AuthenticationManager authenticationManager,
      JwtService jwtService,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      UserService userService
  ){
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
    try {
      String email = body.get("email");
      String password = body.get("password");

      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(email, password)
      );

      User user = userRepository.findByEmail(email)
          .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

      String token = jwtService.generateToken(email);

      RegisterResponse response = new RegisterResponse(
          user.getId(),
          user.getEmail(),
          user.getRole().name(),
          user.getPoints(),
          token
      );

      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
    }
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String admin(){
    return "only admin";
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
    if (userRepository.findByEmail(req.getEmail()).isPresent()) {
      return ResponseEntity
          .badRequest()
          .body("Erro: Email já cadastrado");
    }

    User user = new User();
    user.setEmail(req.getEmail());
    user.setPassword(passwordEncoder.encode(req.getPassword()));

    userService.save(user);

    String token = jwtService.generateToken(req.getEmail());

    RegisterResponse response = new RegisterResponse(
        user.getId(),
        user.getEmail(),
        user.getRole().name(),
        user.getPoints(),
        token
    );

    return ResponseEntity.ok(response);
  }
}