package com.spring_security.spring_security.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring_security.spring_security.model.User;
import com.spring_security.spring_security.repository.UserRepo;

@Service
public class UserService {

  private final UserRepo userRepo;

  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  //save new user
  public User saveUser(User user) {
    String encodedPassword = new BCryptPasswordEncoder(12).encode(user.getPassword());
    user.setPassword(encodedPassword); 
    userRepo.save(user);
    return user;
  }

}
