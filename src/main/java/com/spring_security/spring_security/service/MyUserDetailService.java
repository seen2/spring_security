package com.spring_security.spring_security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring_security.spring_security.model.MyCurrentUserDetails;
import com.spring_security.spring_security.model.User;
import com.spring_security.spring_security.repository.UserRepo;

@Service
public class MyUserDetailService implements UserDetailsService {

  private final UserRepo userRepo;

  public MyUserDetailService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username == null || username.isEmpty()) {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }
    
    User user = userRepo.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    
    return new MyCurrentUserDetails(user);
  }

}
