package com.cleyxds.springcustomers.models;

import com.cleyxds.springcustomers.documents.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedCustomer {

  private String username;
  private String email;

  public UpdatedCustomer(Customer entity) {
    username = entity.getUsername();
    email = entity.getEmail();
  }

}
