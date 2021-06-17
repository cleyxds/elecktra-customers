package net.cleyxds.sqlite.domain.service;

import net.cleyxds.sqlite.domain.model.Customer;
import net.cleyxds.sqlite.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository repository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public List<Customer> findAll() {
    return repository.findAll();
  }

  public Customer fetchById(Long id) {
    Optional<Customer> customer = repository.findById(id);

    return customer.orElse(null);
  }

  public Customer create(Customer customer) {
    String encodedPassword = passwordEncoder.encode(customer.getPassword());

    customer.setPassword(encodedPassword);

    return repository.save(customer);
  }

  public Customer update(Long id, Customer customer) {
    return repository.findById(id).map(updatedCustomer -> {
      updatedCustomer.setUsername(customer.getUsername());
      updatedCustomer.setEmail(customer.getEmail());

      return repository.save(updatedCustomer);
    }).orElse(null);
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }

}
