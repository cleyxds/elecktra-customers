package com.cleyxds.springcustomers.api.security.services;

import com.cleyxds.springcustomers.api.util.JWTUtil;
import com.cleyxds.springcustomers.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthManager implements ReactiveAuthenticationManager {

  @Autowired
  private JWTUtil jwtUtil;

  @Autowired
  private CustomerRepo customerRepo;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    var token = authentication.getCredentials().toString();
    var email = jwtUtil.extractUsername(token);

    return (
      customerRepo.findByEmail(email)
        .flatMap(customer -> {
          if (email.equals(customer.getEmail()) && jwtUtil.isTokenValidated(token)) {
            return Mono.just(authentication);
          } else {
            return Mono.empty();
          }
        })
    );
  }
}
