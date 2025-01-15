package com.spring_security.spring_security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring_security.spring_security.service.JWTService;
import com.spring_security.spring_security.service.MyUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JWTService jwtService;
  private final MyUserDetailService myUserDetailService;

  public JwtAuthFilter(JWTService jwtService, MyUserDetailService myUserDetailService) {
    this.jwtService = jwtService;
    this.myUserDetailService = myUserDetailService;
  }

  @Override@SuppressWarnings("null")
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,  FilterChain filterChain)
      throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token =authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.split(" ")[1]: null;
        if (token != null) {
          String username = jwtService.extractUsername(token);
          if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
            if(jwtService.isTokenValid(token,userDetails)) {
              UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
              authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
          }
        }
        filterChain.doFilter(request, response);
  }
}
