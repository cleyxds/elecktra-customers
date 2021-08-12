package com.cleyxds.springcustomers.api.security;

import com.cleyxds.springcustomers.api.security.services.AuthManager;
import com.cleyxds.springcustomers.api.security.services.CustomerDetailsService;
import com.cleyxds.springcustomers.api.security.services.SecurityContextRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurityConfiguration {

  @Autowired
  private CustomerDetailsService customerDetailsService;

  @Autowired
  private AuthManager authManager;

  @Autowired
  private SecurityContextRepo securityContextRepo;

  @Bean
  @Primary
  ReactiveUserDetailsService userDetailsService() {
    return email -> customerDetailsService.findByUsername(email);
  }

  @Bean
  public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
    return (
      http.authorizeExchange(
        authorizeExchangeSpec -> authorizeExchangeSpec
          .pathMatchers(HttpMethod.POST, "/**").permitAll()
          .anyExchange().authenticated()
      )
      .exceptionHandling()
      .authenticationEntryPoint((response, error) -> Mono.fromRunnable(() ->
        response.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
      .accessDeniedHandler((response, error) -> Mono.fromRunnable(() ->
        response.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
      .and()
      .httpBasic().disable()
      .formLogin().disable()
      .csrf().disable()
      .cors(corsSpec -> {
        var corsConfig = new CorsConfiguration().applyPermitDefaultValues();
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        corsSpec.configurationSource(source);
      })
      .authenticationManager(authManager)
      .securityContextRepository(securityContextRepo)
      .requestCache().requestCache(NoOpServerRequestCache.getInstance())
      .and()
      .build());
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
