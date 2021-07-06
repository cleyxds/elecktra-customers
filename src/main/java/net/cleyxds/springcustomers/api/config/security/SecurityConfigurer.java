package net.cleyxds.springcustomers.api.config.security;

import net.cleyxds.springcustomers.api.filter.JwtRequestFilter;
import net.cleyxds.springcustomers.api.service.CustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Autowired
  private Environment env;

  @Autowired
  private CustomerDetailService customerDetail;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customerDetail);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
      http.headers().frameOptions().disable();
    }

    http.csrf().disable()
            .authorizeRequests()
              .antMatchers("/authenticate").permitAll()
              .antMatchers(HttpMethod.POST, "/customers").permitAll()
              .antMatchers(HttpMethod.POST, "/images/**").permitAll()
              .antMatchers("/images/resource/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    http.cors();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
    configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
