package com.palpita.palpita.users.controllers;

import com.palpita.palpita.auth.dto.UserResponse;
import com.palpita.palpita.users.entity.User;
import com.palpita.palpita.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
  private final UserService userService;

  public UsersController(UserService userService){
    this.userService = userService;
  }

  @GetMapping("/{userId}/makeAdmin")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> makeAdmin(@PathVariable Long userId){
    User user = userService.makeAdmin(userId);
    UserResponse userResponse = new UserResponse(
        user.getId(),
        user.getEmail(),
        user.getRole().name(),
        user.getPoints(),
        user.getBets(),
        ""
    );
    return ResponseEntity.ok(userResponse);
  }
}
