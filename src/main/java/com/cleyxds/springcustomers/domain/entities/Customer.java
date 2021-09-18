package com.cleyxds.springcustomers.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customers")
public class Customer {

  @Id
  @Column(nullable = false, unique = true)
  private Long id;

  @Column
  private String name;

  @Column(nullable = false)
  private String username;

  @Column(length = 60, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @OneToOne(cascade = CascadeType.ALL)
  private CustomerImage image;

  @Column(name = "created_at")
  private String createdAt;

  private Integer devices;

}