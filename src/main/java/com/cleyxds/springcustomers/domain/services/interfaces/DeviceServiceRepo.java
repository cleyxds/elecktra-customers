package com.cleyxds.springcustomers.domain.services.interfaces;

import com.cleyxds.springcustomers.domain.entities.Customer;
import com.cleyxds.springcustomers.domain.entities.Device;
import org.springframework.stereotype.Service;

@Service
public interface DeviceServiceRepo {

  Device addDeviceToCustomer(String id, Customer customer);

}
