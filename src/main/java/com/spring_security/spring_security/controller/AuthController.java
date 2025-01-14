package com.spring_security.spring_security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring_security.spring_security.model.LoginUser;
import com.spring_security.spring_security.model.User;
import com.spring_security.spring_security.service.AuthService;
import com.spring_security.spring_security.service.UserService;

@RestController
public class AuthController {

  private final UserService userService;
  private final AuthService authService;

  public AuthController(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginUser loginUser) {
    return authService.verifyLogin(loginUser);
  }


  @PostMapping("/register")
  public User register(@RequestBody User user) {
    userService.saveUser(user);

    return user;
  }

}
