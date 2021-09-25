package com.cleyxds.springcustomers.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.cleyxds.springcustomers.domain.entities.Customer;
import com.cleyxds.springcustomers.domain.entities.Device;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {

  private Long id;
  private String name;
  private String login;
  private List<String> devices;
  private String avatar_url;
  private Date created_at;

  public CustomerDTO(Customer entity) {
    id = entity.getId();
    name = entity.getName();
    login = entity.getUsername();
    devices = getDevicesId(entity);
    avatar_url = null;
    created_at = entity.getCreatedAt();
  }

  private List<String> getDevicesId(Customer entity) {
    return (
      entity.getDevices().stream()
        .map(Device::getId)
        .collect(Collectors.toList())
    );
  }

  public static CustomerDTO from(Customer entity) {
    return new CustomerDTO(entity);
  }

}
