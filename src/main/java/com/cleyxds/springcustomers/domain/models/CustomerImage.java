package com.cleyxds.springcustomers.domain.models;

import com.cleyxds.springcustomers.domain.services.ImageIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
