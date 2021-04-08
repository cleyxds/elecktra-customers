package net.cleyxds.sqlite.api.controller;

import net.cleyxds.sqlite.domain.model.Customer;
import net.cleyxds.sqlite.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  private CustomerRepository repository;

  @GetMapping
  public List<Customer> list() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Customer fetch(@PathVariable Long id) {
    Optional<Customer> customer = repository.findById(id);

    return customer.orElse(null);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Customer create(@RequestBody Customer customer) {
    customer = repository.save(customer);

    return customer;
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Customer update(@PathVariable Long id, @RequestBody Customer customer) {
    return repository.findById(id).map(updatedCustomer -> {
      updatedCustomer.setName(customer.getName());
      updatedCustomer.setPhone(customer.getPhone());
      return repository.save(updatedCustomer);
    }).orElse(null);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    repository.deleteById(id);
  }

}
