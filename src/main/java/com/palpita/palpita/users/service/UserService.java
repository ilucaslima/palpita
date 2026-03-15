package com.palpita.palpita.users.service;

import com.palpita.palpita.users.entity.Role;
import com.palpita.palpita.users.entity.User;
import com.palpita.palpita.users.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Optional<User> findByEmail(String email){
    return userRepository.findByEmail(email);
  }

  public User save(User user){
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(Role.USER);

    return userRepository.save(user);
  }
}
