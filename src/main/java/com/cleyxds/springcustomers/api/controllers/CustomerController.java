package com.cleyxds.springcustomers.api.controllers;

import com.cleyxds.springcustomers.api.dtos.CustomerDTO;
import com.cleyxds.springcustomers.api.exceptions.StorageFileNotFoundException;
import com.cleyxds.springcustomers.domain.models.Customer;
import com.cleyxds.springcustomers.domain.services.impl.ImageServiceImpl;
import com.cleyxds.springcustomers.domain.services.impl.CustomerServiceImpl;
import com.cleyxds.springcustomers.api.exceptions.StorageException;
import com.cleyxds.springcustomers.domain.services.interfaces.CustomerServiceRepo;
import com.cleyxds.springcustomers.domain.services.interfaces.ImageServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  private CustomerServiceRepo customerService;

  @Autowired
  private ImageServiceRepo imageService;

  @GetMapping
  public List<CustomerDTO> list() {
    return customerService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerDTO> fetch(@PathVariable Long id) {
    var customer = customerService.fetchDTOById(id);

    customerService.attachAvatarUrl(customer);

    return ResponseEntity.status(HttpStatus.OK).body(customer);
  }

  @PostMapping
  public ResponseEntity<CustomerDTO> create(@RequestBody Customer customer) {
    var customerCreated = customerService.create(customer);

    customerService.attachAvatarUrl(customerCreated);

    return ResponseEntity.status(HttpStatus.CREATED).body(customerCreated);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
    customerService.update(id, customer);

    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    var hasImage = imageService.loadPathById(id) != null;

    customerService.delete(id, hasImage);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @ExceptionHandler({StorageFileNotFoundException.class, StorageException.class})
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc, StorageException exc2) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

}
