package com.cleyxds.springcustomers.handlers;

import com.cleyxds.springcustomers.api.dto.CustomerDTO;
import com.cleyxds.springcustomers.documents.Customer;
import com.cleyxds.springcustomers.services.interfaces.CustomerService;
import com.cleyxds.springcustomers.models.UpdatedCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
public class CustomerHandler {

  @Autowired
  private CustomerService service;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public Mono<ServerResponse> create(ServerRequest request) {
    var customer = request.bodyToMono(Customer.class)
      .flatMap(entity -> {
        var encodedPassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);
        entity.setCreatedAt(new Date().toString());
        entity.setDevices(0);
        entity.setAvatar_url(null);
        return service.save(entity);
      });

    return customer
      .flatMap(c -> ServerResponse.status(HttpStatus.CREATED).bodyValue(new CustomerDTO(c)));
  }

  public Mono<ServerResponse> fetch(ServerRequest request) {
    var id = request.pathVariable("id");

    if (id.isBlank()) {
      return ServerResponse.badRequest().build();
    }

    return (
      service.findById(id)
        .flatMap(customer -> {
          var formedCustomer = new CustomerDTO(customer);
          return ServerResponse.ok().bodyValue(formedCustomer);
        })
        .switchIfEmpty(ServerResponse.notFound().build())
    );
  }

  public Mono<ServerResponse> list(ServerRequest request) {
    var customers = service.findAll();
    var formedCustomers = customers.map(CustomerDTO::new);

    return (
      ServerResponse
        .ok()
        .body(formedCustomers, CustomerDTO.class)
    );
  }

  public Mono<ServerResponse> update(ServerRequest request) {
    var id = request.pathVariable("id");

    if (id.isBlank()) {
      return ServerResponse.badRequest().build();
    }

    return (
      service.findById(id)
        .flatMap(customer -> {
          var updatedCustomer = request.bodyToMono(UpdatedCustomer.class)
            .flatMap(entity -> {
              customer.setEmail(entity.getEmail());
              customer.setUsername(entity.getUsername());
              return service.save(customer);
            });

          return ServerResponse.ok().body(updatedCustomer, UpdatedCustomer.class);
        })
        .switchIfEmpty(ServerResponse.notFound().build())
    );
  }

  public Mono<ServerResponse> delete(ServerRequest request) {
    var id = request.pathVariable("id");

    if (id.isBlank()) {
      return ServerResponse.badRequest().build();
    }

    return service.delete(id)
      .flatMap(customer -> ServerResponse.noContent().build())
      .switchIfEmpty(ServerResponse.notFound().build());
  }

}
