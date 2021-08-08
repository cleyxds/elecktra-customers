package com.cleyxds.springcustomers.documents;

import com.cleyxds.springcustomers.models.CustomerImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

  @Id
  private String id;
  private String name;
  private String username;
  private String email;
  private String password;
  private CustomerImage avatar_url;
  private String createdAt;
  private Integer devices;

}
