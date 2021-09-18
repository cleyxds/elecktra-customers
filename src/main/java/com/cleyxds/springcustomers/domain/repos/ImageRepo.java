package com.cleyxds.springcustomers.domain.repos;

import com.cleyxds.springcustomers.domain.entities.CustomerImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<CustomerImage, Long> {
}
