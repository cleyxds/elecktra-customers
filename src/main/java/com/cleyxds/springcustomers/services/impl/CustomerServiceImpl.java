package com.cleyxds.springcustomers.services.impl;

import com.cleyxds.springcustomers.documents.Customer;
import com.cleyxds.springcustomers.services.interfaces.CustomerService;
import com.cleyxds.springcustomers.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepo repo;

  @Override
  public Flux<Customer> findAll() {
    return repo.findAll();
  }

  @Override
  public Mono<Customer> findById(String id) {
    return repo.findById(id);
  }

  @Override
  public Mono<Customer> save(Customer customer) {
    return repo.save(customer);
  }

  @Override
  public Mono<Customer> delete(String id) {
    return (
      repo.findById(id)
        .flatMap(customer -> repo.deleteById(customer.getId())
        .thenReturn(customer))
    );
  }

}
