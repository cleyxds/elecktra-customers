package net.cleyxds.springcustomers.api.service;

import net.cleyxds.springcustomers.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerDetailService implements UserDetailsService {

  @Autowired
  private CustomerService service;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var customer = service.fetchByEmail(username);

    return new User(customer.getEmail(), customer.getPassword(), new ArrayList<>());
  }
}
