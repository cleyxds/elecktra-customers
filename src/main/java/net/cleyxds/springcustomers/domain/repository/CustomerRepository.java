package net.cleyxds.springcustomers.domain.repository;

import net.cleyxds.springcustomers.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Customer findByEmail(String username);

}
