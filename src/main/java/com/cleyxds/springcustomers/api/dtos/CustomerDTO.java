package com.cleyxds.springcustomers.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.cleyxds.springcustomers.domain.models.Customer;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {

  private Long id;
  private String name;
  private String login;
  private Integer devices;
  private String avatar_url;
  private String created_at;

  public CustomerDTO(Customer entity) {
    id = entity.getId();
    name = entity.getName();
    login = entity.getUsername();
    devices = entity.getDevices();
    avatar_url = null;
    created_at = entity.getCreatedAt();
  }

}
