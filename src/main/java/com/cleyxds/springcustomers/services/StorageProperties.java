package com.cleyxds.springcustomers.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@ConfigurationProperties("storage")
@Configuration
public class StorageProperties {

	private String location = "images";

}
