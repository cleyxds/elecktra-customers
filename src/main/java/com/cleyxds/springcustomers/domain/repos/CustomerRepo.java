package com.cleyxds.springcustomers.domain.repos;

import com.cleyxds.springcustomers.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

  Customer findByEmail(String username);

}
