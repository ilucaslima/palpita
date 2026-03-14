package com.palpita.palpita.controller;

import com.palpita.palpita.security.JwtService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthController(
      AuthenticationManager authenticationManager,
      JwtService jwtService
  ){
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  public Map<String, String> login(@RequestBody Map<String,String> body){

    String email = body.get("email");
    String password = body.get("password");

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(email,password)
    );

    String token = jwtService.generateToken(email);

    return Map.of("token", token);
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String admin(){
    return "only admin";
  }
}