package net.cleyxds.sqlite.domain.repository;

import net.cleyxds.sqlite.domain.model.CustomerImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<CustomerImage, Long> {
}
