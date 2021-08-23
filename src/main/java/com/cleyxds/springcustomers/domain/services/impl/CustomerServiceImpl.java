package com.cleyxds.springcustomers.domain.services.impl;

import com.cleyxds.springcustomers.api.dtos.CustomerDTO;
import com.cleyxds.springcustomers.domain.repos.CustomerRepo;
import com.cleyxds.springcustomers.domain.repos.ImageRepo;
import com.cleyxds.springcustomers.domain.services.interfaces.CustomerServiceRepo;
import lombok.SneakyThrows;
import com.cleyxds.springcustomers.domain.models.Customer;
import com.cleyxds.springcustomers.domain.models.CustomerImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerServiceRepo {

  @Autowired
  private CustomerRepo repository;

  @Autowired
  private ImageServiceImpl imageServiceImpl;

  @Autowired
  private ImageRepo imageRepo;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public List<CustomerDTO> findAll() {
    var customers = repository.findAll();

    return (
      customers.stream()
        .map(CustomerDTO::new)
        .map(this::attachAvatarUrl)
        .collect(Collectors.toList())
    );
  }

  public Customer fetchById(Long id) {
    var customer = repository.findById(id);

    return customer.orElse(null);
  }

  public CustomerDTO fetchDTOById(Long id) {
    var customer = repository.findById(id).map(CustomerDTO::new);

    return customer.orElse(null);
  }

  public CustomerDTO create(Customer customer) {
    var encodedPassword = passwordEncoder.encode(customer.getPassword());

    customer.setPassword(encodedPassword);
    customer.setCreatedAt(LocalDate.now().toString());
    customer.setDevices(0);
    customer.setImage(new CustomerImage());

    var createdCustomer = repository.save(customer);

    return new CustomerDTO(createdCustomer);
  }

  public Customer update(Long id, Customer customer) {
    return repository.findById(id).map(updatedCustomer -> {
      updatedCustomer.setUsername(customer.getUsername());
      updatedCustomer.setEmail(customer.getEmail());

      return repository.save(updatedCustomer);
    }).orElse(null);
  }

  public void delete(Long id, Boolean hasImage) {
    if (hasImage) {
      imageServiceImpl.deleteById(id);
    }
    repository.deleteById(id);
  }

  public Customer fetchByEmail(String email) {
    return repository.findByEmail(email);
  }

  public void attachImage(Long id, String path) {
    imageRepo.save(new CustomerImage(id, path, fetchById(id)));
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

}
