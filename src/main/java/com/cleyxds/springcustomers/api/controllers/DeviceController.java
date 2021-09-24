package com.cleyxds.springcustomers.api.controllers;

import com.cleyxds.springcustomers.api.models.DeviceForm;
import com.cleyxds.springcustomers.domain.services.interfaces.CustomerServiceRepo;
import com.cleyxds.springcustomers.domain.services.interfaces.DeviceServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

  @Autowired
  private DeviceServiceRepo deviceService;

  @Autowired
  private CustomerServiceRepo customerService;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody DeviceForm form) {
    var customer = customerService.fetchById(form.getCustomerId());

    var device = deviceService
      .addDeviceToCustomer(
        form.getDeviceId(),
        customer
      );

    return (
      ResponseEntity
        .status(HttpStatus.CREATED)
        .body(device.getId())
    );
  }

}
