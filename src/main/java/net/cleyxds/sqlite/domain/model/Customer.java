package net.cleyxds.sqlite.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(length = 11, nullable = false)
  private String phone;

}
