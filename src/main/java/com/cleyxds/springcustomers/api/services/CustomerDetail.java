package com.cleyxds.springcustomers.api.services;

import com.cleyxds.springcustomers.domain.services.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDetail implements UserDetailsService {

  @Autowired
  private CustomerServiceImpl customerService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var customer = customerService.fetchByEmail(username);

    return (
      new User(
        customer.getEmail(),
        customer.getPassword(),
        List.of()
      )
    );
  }
}
