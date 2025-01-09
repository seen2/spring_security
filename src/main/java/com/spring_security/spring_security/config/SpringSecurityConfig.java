package com.spring_security.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.spring_security.spring_security.service.MyUserDetailService;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {

  private final MyUserDetailService myUserDetailsService;

  public SpringSecurityConfig(MyUserDetailService myUserDetailsService) {
    this.myUserDetailsService = myUserDetailsService;
  }

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

  // @Bean
  // public UserDetailsService myUserDetailsService() {

  // var kavya =
  // User.withUsername("kavya").password(passwordEncoder().encode("K@123")).roles("USER").build();
  // var pratik =
  // User.withUsername("pratik").password(passwordEncoder().encode("P@123")).roles("USER").build();

  // return new InMemoryUserDetailsManager(kavya, pratik);
  // }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(myUserDetailsService);
    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

}
