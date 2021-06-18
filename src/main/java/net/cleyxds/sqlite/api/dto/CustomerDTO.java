package net.cleyxds.sqlite.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.cleyxds.sqlite.domain.model.Customer;
import net.cleyxds.sqlite.domain.model.CustomerImage;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {

  private Long id;
  private String name;
  private String login;
  private Integer devices;
  private String avatar_url;
  private LocalDate created_at;

  public CustomerDTO(Customer entity) {
    id = entity.getId();
    name = entity.getName();
    login = entity.getUsername();
    devices = entity.getDevices();
    avatar_url = entity.getImage().getPath();
    created_at = entity.getCreatedAt();
  }

}
