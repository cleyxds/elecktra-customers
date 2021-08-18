package net.cleyxds.springcustomers.api.controller;

import net.cleyxds.springcustomers.api.dto.CustomerDTO;
import net.cleyxds.springcustomers.api.exception.StorageException;
import net.cleyxds.springcustomers.api.exception.StorageFileNotFoundException;
import net.cleyxds.springcustomers.domain.model.Customer;
import net.cleyxds.springcustomers.domain.service.CustomerService;
import net.cleyxds.springcustomers.domain.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  private CustomerService service;

  @Autowired
  private ImageService imageService;

  @GetMapping
  public List<CustomerDTO> list() {
    var customers = service.findAll();
    return customers;
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerDTO> fetch(@PathVariable Long id) {
    var customer = service.fetchDTOById(id);

    service.attachAvatarUrl(customer);

    return ResponseEntity.ok(customer);
  }

  @PostMapping
  public ResponseEntity<CustomerDTO> create(@RequestBody Customer customer) {
    var customerCreated = service.create(customer);

    service.attachAvatarUrl(customerCreated);

    return ResponseEntity.status(HttpStatus.CREATED).body(customerCreated);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
    service.update(id, customer);

    return ResponseEntity.accepted().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    var hasImage = imageService.loadPathById(id) != null;

    service.delete(id, hasImage);

    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler({StorageFileNotFoundException.class, StorageException.class})
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc, StorageException exc2) {
    return ResponseEntity.notFound().build();
  }

}
