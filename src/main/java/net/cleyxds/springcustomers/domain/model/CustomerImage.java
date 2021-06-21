package net.cleyxds.springcustomers.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.cleyxds.springcustomers.domain.service.ImageIdGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "images")
public class CustomerImage {

  @Id
  @GeneratedValue(generator = ImageIdGenerator.GENERATOR_NAME)
  private Long id;

  private String path;

  @OneToOne
  private Customer customer;

  public CustomerImage(Long id, String path) {
    this.id = id;
    this.path = path;
  }
}
