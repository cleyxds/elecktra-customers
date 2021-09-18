package com.cleyxds.springcustomers.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.cleyxds.springcustomers.api.models.AuthResponse;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO implements Serializable {

  private String jwt;
  private CustomerDTO customer;

  public AuthResponseDTO(AuthResponse entity, CustomerDTO customerDTOEntity) {
    jwt = entity.getJwt();
    customer = customerDTOEntity;
  }

}
