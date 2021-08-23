package com.cleyxds.springcustomers.domain.repos;

import com.cleyxds.springcustomers.domain.models.CustomerImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<CustomerImage, Long> {
}
