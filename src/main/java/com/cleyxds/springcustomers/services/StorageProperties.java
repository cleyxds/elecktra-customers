package com.cleyxds.springcustomers.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Setter
@Getter
@ConfigurationProperties("storage")
@Configuration
public class StorageProperties {

	private String location = "database/images";

}
