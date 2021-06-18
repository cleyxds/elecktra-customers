package net.cleyxds.sqlite.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.cleyxds.sqlite.domain.service.ImageIdGenerator;
import org.hibernate.annotations.GenericGenerator;

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
