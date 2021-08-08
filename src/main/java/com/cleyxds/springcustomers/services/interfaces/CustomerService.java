package com.cleyxds.springcustomers.services.interfaces;

import com.cleyxds.springcustomers.documents.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

  Flux<Customer> findAll();
  Mono<Customer> findById(String id);
  Mono<Customer> save(Customer customer);
  Mono<Customer> delete(String id);

}
