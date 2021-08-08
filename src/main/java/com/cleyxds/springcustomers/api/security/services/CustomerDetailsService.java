package com.cleyxds.springcustomers.api.security.services;

import com.cleyxds.springcustomers.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerDetailsService implements ReactiveUserDetailsService {

  @Autowired
  private CustomerRepo customerRepo;

  @Override
  public Mono<UserDetails> findByUsername(String email) {
    return (
      customerRepo.findByEmail(email)
        .flatMap(customer ->
          Mono.just(User.withUsername(customer.getEmail()).build()))
    );
  }
}
