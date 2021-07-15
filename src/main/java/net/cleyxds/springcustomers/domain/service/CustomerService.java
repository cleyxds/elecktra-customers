package net.cleyxds.springcustomers.domain.service;

import net.cleyxds.springcustomers.api.dto.CustomerDTO;
import net.cleyxds.springcustomers.domain.model.Customer;
import net.cleyxds.springcustomers.domain.model.CustomerImage;
import net.cleyxds.springcustomers.domain.repository.CustomerRepository;
import net.cleyxds.springcustomers.domain.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository repository;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public List<CustomerDTO> findAll() {
    List<Customer> customers = repository.findAll();

    return customers.stream().map(CustomerDTO::new).collect(Collectors.toList());
  }

  public Customer fetchById(Long id) {
    Optional<Customer> customer = repository.findById(id);

    return customer.orElse(null);
  }

  public CustomerDTO fetchDTOById(Long id) {
    Optional<CustomerDTO> customer = repository.findById(id).map(CustomerDTO::new);

    return customer.orElse(null);
  }

  public Customer create(Customer customer) {
    String encodedPassword = passwordEncoder.encode(customer.getPassword());

    customer.setPassword(encodedPassword);
    customer.setCreatedAt(LocalDate.now().toString());
    customer.setDevices(0);
    customer.setImage(new CustomerImage());

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

  public Customer fetchByEmail(String email) {
    return repository.findByEmail(email);
  }

  public void attachImage(Long id, String path) {
    imageRepository.save(new CustomerImage(id, path, fetchById(id)));
  }

}
