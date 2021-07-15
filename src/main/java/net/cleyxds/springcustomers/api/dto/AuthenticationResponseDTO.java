package net.cleyxds.springcustomers.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.cleyxds.springcustomers.domain.model.AuthenticationResponse;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO implements Serializable {

  private String jwt;
  private CustomerDTO customer;

  public AuthenticationResponseDTO(AuthenticationResponse entity, CustomerDTO customerDTOEntity) {
    jwt = entity.getJwt();
    customer = customerDTOEntity;
  }

}
