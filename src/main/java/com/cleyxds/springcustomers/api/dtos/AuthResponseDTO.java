package com.cleyxds.springcustomers.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO implements Serializable {

  private String jwt;
  private CustomerDTO customer;

  public static AuthResponseDTO from(String jwt, CustomerDTO customer) {
    return new AuthResponseDTO(jwt, customer);
  }

}
