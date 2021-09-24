package com.cleyxds.springcustomers.domain.repos;

import com.cleyxds.springcustomers.domain.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepo extends JpaRepository<Device, String> {
}
