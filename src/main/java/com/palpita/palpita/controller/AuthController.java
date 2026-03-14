package com.palpita.palpita.controller;

import com.palpita.palpita.entity.User;
import com.palpita.palpita.security.JwtService;
import com.palpita.palpita.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public AuthController(
      UserService userService,
      JwtService jwtService,
      PasswordEncoder passwordEncoder
  ){
    this.userService = userService;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/login")
  public Map<String, String> login(@RequestBody Map<String, String> body){
    String email = body.get("email");
    String password = body.get("password");


    Optional<User> user = userService.findByEmail(email);

    if(user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())){
      String token = jwtService.generateToken(email);

      return Map.of("token", token);
    }

    throw new RuntimeException("Invalid credentials");
  }
}
