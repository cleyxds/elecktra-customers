package com.cleyxds.springcustomers.handlers;

import com.cleyxds.springcustomers.api.dto.AuthResponseDTO;
import com.cleyxds.springcustomers.api.dto.CustomerDTO;
import com.cleyxds.springcustomers.api.util.JWTUtil;
import com.cleyxds.springcustomers.models.AuthRequest;
import com.cleyxds.springcustomers.models.AuthResponse;
import com.cleyxds.springcustomers.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthHander {

  @Autowired
  private CustomerRepo customerRepo;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private JWTUtil jwtUtil;

  public Mono<ServerResponse> getToken(ServerRequest request) {
    var authForm = request.bodyToMono(AuthRequest.class);

    return (
      authForm
        .flatMap(customer -> customerRepo.findByEmail(customer.getEmail())
          .flatMap(customerDetail -> {
            if (passwordEncoder.matches(customer.getPassword(), customerDetail.getPassword())) {
              var jwt = jwtUtil.generateToken(customerDetail.getEmail());
              return ServerResponse
                .ok()
                .bodyValue(new AuthResponseDTO(jwt, new CustomerDTO(customerDetail)));
            } else {
              return ServerResponse.badRequest().build();
            }
          })
        .switchIfEmpty(ServerResponse.badRequest().build())
        )
    );
  }

}
