package com.cleyxds.springcustomers.api.dto;

import com.cleyxds.springcustomers.documents.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {

  private String id;
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
