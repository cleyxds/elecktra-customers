package com.cleyxds.springcustomers.domain.entities;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customers")
public class Customer {

  @Id
  @Column(nullable = false, unique = true)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column
  private String username;

  @Column(length = 60, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(name = "created_at")
  private Date createdAt;

  @OneToMany(cascade=ALL, mappedBy="customer")
  private List<Device> devices;

}
