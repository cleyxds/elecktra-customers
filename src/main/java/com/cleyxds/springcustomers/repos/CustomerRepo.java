package com.cleyxds.springcustomers.repos;

import com.cleyxds.springcustomers.documents.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepo extends ReactiveMongoRepository<Customer, String> {

  Mono<Customer> findByEmail(String email);

}
