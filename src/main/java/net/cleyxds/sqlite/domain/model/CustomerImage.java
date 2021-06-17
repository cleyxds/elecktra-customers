package net.cleyxds.sqlite.domain.model;

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
  private Long id;

  @Column
  private String path;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  private Customer customer;

}
