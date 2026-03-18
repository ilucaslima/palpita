package com.palpita.palpita.auth.controller;

import com.palpita.palpita.auth.dto.UserRequest;
import com.palpita.palpita.auth.dto.UserResponse;
import com.palpita.palpita.users.entity.User;
import com.palpita.palpita.security.JwtService;

import com.palpita.palpita.users.service.UserService;
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
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  public AuthController(
      AuthenticationManager authenticationManager,
      JwtService jwtService,
      PasswordEncoder passwordEncoder,
      UserService userService
  ){
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
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

      User user = userService.findByEmail(email)
          .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

      String token = jwtService.generateToken(email);

      UserResponse response = new UserResponse(
          user.getId(),
          user.getEmail(),
          user.getRole().name(),
          user.getPoints(),
          user.getBets(),
          token
      );

      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User req) {
    return ResponseEntity.ok(userService.save(req));
  }
}