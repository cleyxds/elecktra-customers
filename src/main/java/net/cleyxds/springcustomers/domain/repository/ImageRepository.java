package net.cleyxds.springcustomers.domain.repository;

import net.cleyxds.springcustomers.domain.model.CustomerImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<CustomerImage, Long> {
}
