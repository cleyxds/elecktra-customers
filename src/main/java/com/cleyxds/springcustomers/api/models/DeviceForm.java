package com.cleyxds.springcustomers.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceForm {

  private String deviceId;
  private Long customerId;

}
