package com.spring_security.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring_security.spring_security.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

  User findByUsername(String username);

}
