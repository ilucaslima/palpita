package com.palpita.palpita.controller;

import com.palpita.palpita.dto.RegisterRequest;
import com.palpita.palpita.entity.Role;
import com.palpita.palpita.entity.User;
import com.palpita.palpita.repository.UserRepository;
import com.palpita.palpita.security.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
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

  public AuthController(
      AuthenticationManager authenticationManager,
      JwtService jwtService,
      UserRepository userRepository, PasswordEncoder passwordEncoder){
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
    try {
      String email = body.get("email");
      String password = body.get("password");

      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(email, password)
      );

      String token = jwtService.generateToken(email);
      return ResponseEntity.ok(Map.of("token", token));

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
  public String register(@RequestBody RegisterRequest req) {
    if (userRepository.findByEmail(req.getEmail()).isPresent()) {
      return "Erro: email já cadastrado";
    }

    User user = new User();
    user.setEmail(req.getEmail());
    user.setPassword(passwordEncoder.encode(req.getPassword()));
    user.setRole(Role.USER); //default;

    userRepository.save(user);

    return "Usuário registrado com sucesso!";
  }

}