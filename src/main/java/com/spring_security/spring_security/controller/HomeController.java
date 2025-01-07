package com.spring_security.spring_security.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController {

  @GetMapping("/")
  public String getHome(HttpServletRequest request) {
    return "Hello World: " + request.getSession().getId() ;
  }

  @GetMapping("/csrf")
  public CsrfToken getToken(HttpServletRequest request) {
    return (CsrfToken) request.getAttribute("_csrf");
  }

  @PostMapping("/")
  public String postHome(HttpServletRequest request) {
    return "Hello World: " + request.getSession().getId();
  }

}
