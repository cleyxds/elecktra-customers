package com.cleyxds.springcustomers.domain.services.impl;

import com.cleyxds.springcustomers.api.dtos.CustomerDTO;
import com.cleyxds.springcustomers.domain.repos.CustomerRepo;
import com.cleyxds.springcustomers.domain.services.interfaces.CustomerServiceRepo;
import com.cleyxds.springcustomers.domain.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

@Service
public class CustomerServiceImpl implements CustomerServiceRepo {

  @Autowired
  private CustomerRepo repo;

  @Autowired
  private ImageServiceImpl imageServiceImpl;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public List<CustomerDTO> findAll() {
    var customers = repo.findAll();

    return (
      customers.stream()
        .map(CustomerDTO::from)
        .map(this::attachAvatarUrl)
        .collect(Collectors.toList())
    );
  }

  public Customer fetchById(Long id) {
    var customer = repo.findById(id);

    return customer.orElse(null);
  }

  public CustomerDTO fetchDTOById(Long id) {
    var customer = repo.findById(id).map(CustomerDTO::from);

    return customer.orElse(null);
  }

  public CustomerDTO create(Customer customer) {
    var encodedPassword = passwordEncoder.encode(customer.getPassword());

    customer.setId(generateUID());
    customer.setPassword(encodedPassword);
    customer.setCreatedAt(Date.from(Instant.now()));
    customer.setDevices(List.of());

    var createdCustomer = repo.save(customer);

    return CustomerDTO.from(createdCustomer);
  }

  public Customer update(Long id, Customer customer) {
    return repo.findById(id).map(updatedCustomer -> {
      updatedCustomer.setUsername(customer.getUsername());
      updatedCustomer.setEmail(customer.getEmail());

      return repo.save(updatedCustomer);
    }).orElse(null);
  }

  public void delete(Long id, Boolean hasImage) {
    if (hasImage) {
      imageServiceImpl.deleteById(id);
    }
    repo.deleteById(id);
  }

  public Customer fetchByEmail(String email) {
    return repo.findByEmail(email);
  }

  @SneakyThrows
  public CustomerDTO attachAvatarUrl(CustomerDTO customerDTO) {
    try {
      customerDTO.setAvatar_url(imageServiceImpl.loadImageById(customerDTO.getId()).toString());
    } catch (NoSuchElementException e) {
      customerDTO.setAvatar_url(null);
    }
    return customerDTO;
  }

  private Long generateUID() {
    var generatedId = UUID.randomUUID().toString();
    generatedId = generatedId.replace("-","").replaceAll("[a-z]", "");
    generatedId = generatedId.substring(0, 8);
    return Long.parseLong(generatedId);
  }

}
