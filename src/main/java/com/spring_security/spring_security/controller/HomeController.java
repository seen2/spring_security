package com.spring_security.spring_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class HomeController {

  @GetMapping("/")
  public String getMethodName(HttpServletRequest  request) {
      return "Hello World: "+request.getSession().getId();
  }
  

}
