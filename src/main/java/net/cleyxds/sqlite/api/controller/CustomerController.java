package net.cleyxds.sqlite.api.controller;

import net.cleyxds.sqlite.api.dto.CustomerDTO;
import net.cleyxds.sqlite.domain.model.Customer;
import net.cleyxds.sqlite.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  private CustomerService service;

  @GetMapping
  public List<CustomerDTO> list() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public CustomerDTO fetch(@PathVariable Long id) {
    return service.fetchDTOById(id);
  }

  @PostMapping
  public ResponseEntity<Customer> create(@RequestBody Customer customer) {
    service.create(customer);

    return ResponseEntity.created(null).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
    service.update(id, customer);

    return ResponseEntity.created(null).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    service.delete(id);

    return ResponseEntity.noContent().build();
  }

}
