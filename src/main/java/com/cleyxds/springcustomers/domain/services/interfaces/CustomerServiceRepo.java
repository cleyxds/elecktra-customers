package com.cleyxds.springcustomers.domain.services.interfaces;

import com.cleyxds.springcustomers.api.dtos.CustomerDTO;
import com.cleyxds.springcustomers.domain.models.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerServiceRepo {

  CustomerDTO create(Customer customer);
  List<CustomerDTO> findAll();
  CustomerDTO fetchDTOById(Long id);
  Customer fetchById(Long id);
  Customer update(Long id, Customer customer);
  void delete(Long id, Boolean hasImage);
  Customer fetchByEmail(String email);
  void attachImage(Long id, String path);
  CustomerDTO attachAvatarUrl(CustomerDTO customerDTO);

}
