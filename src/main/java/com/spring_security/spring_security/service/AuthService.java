package com.spring_security.spring_security.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring_security.spring_security.model.LoginUser;
import com.spring_security.spring_security.model.User;
import com.spring_security.spring_security.repository.UserRepo;

@Service
public class AuthService {

  private final UserRepo userRepo;
  private final JWTService jwtService;

  public AuthService(UserRepo userRepo, JWTService jwtService) {
    this.jwtService = jwtService;
    this.userRepo = userRepo;
  }

  private boolean isAuthenticated(LoginUser loginUser) {

    if (loginUser == null) {
      return false;
    } else {
      User user = userRepo.findByUsername(loginUser.username());

      if (user == null) {
        return false;
      } else {
        // verify with bcrypt
        return new BCryptPasswordEncoder(12).matches(loginUser.password(), user.getPassword());
      }
    }

  }

  public String verifyLogin(LoginUser loginUser) {
    if (isAuthenticated(loginUser)) {
      return jwtService.generateToken(loginUser.username());

    } else {
      return "error";
    }
  }
}
