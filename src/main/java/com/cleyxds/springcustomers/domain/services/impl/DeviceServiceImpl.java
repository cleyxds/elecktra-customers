package com.cleyxds.springcustomers.domain.services.impl;

import com.cleyxds.springcustomers.domain.entities.Customer;
import com.cleyxds.springcustomers.domain.entities.Device;
import com.cleyxds.springcustomers.domain.repos.DeviceRepo;
import com.cleyxds.springcustomers.domain.services.interfaces.DeviceServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceServiceRepo {

  @Autowired
  private DeviceRepo repo;

  public Device addDeviceToCustomer(String id, Customer customer) {
    var device = new Device();

    device.setId(id);
    device.setCustomer(customer);
    repo.save(device);
    return device;
  }

}
