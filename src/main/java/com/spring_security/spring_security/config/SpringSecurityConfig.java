package com.spring_security.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // lambda way
    http.csrf(customizer -> customizer.disable()); // disables csrf
    http.authorizeHttpRequests(requests -> requests.anyRequest().authenticated()); // any request is authenticated
    // http.formLogin(Customizer.withDefaults()); //enables form login
    http.httpBasic(Customizer.withDefaults()); // enables postman login
    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // enables
                                                                                                       // different
                                                                                                       // session id for
                                                                                                       // every request

    // imperative way
    /*
     * var customizer = new Customizer<CsrfConfigurer<HttpSecurity>>() {
     * 
     * @Override
     * public void customize(CsrfConfigurer<HttpSecurity> customizer) {
     * customizer.disable();
     * }
     * };
     * http.csrf(customizer);
     */
    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {

    var kavya = User.withUsername("kavya").password(passwordEncoder().encode("K@123")).roles("USER").build();
    var pratik = User.withUsername("pratik").password(passwordEncoder().encode("P@123")).roles("USER").build();

    return new InMemoryUserDetailsManager(kavya, pratik);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
