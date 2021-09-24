package com.cleyxds.springcustomers.domain.services.interfaces;

import com.cleyxds.springcustomers.domain.entities.Customer;
import com.cleyxds.springcustomers.domain.entities.Device;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceServiceRepo {

  Device addDeviceToCustomer(String id, Customer customer);

}
