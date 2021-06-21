package net.cleyxds.springcustomers.api.config.security;

import net.cleyxds.springcustomers.api.service.CustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class SecurityConfigurer {

  @Autowired
  private CustomerDetailService customerDetailService;

  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customerDetailService);
  }

}
