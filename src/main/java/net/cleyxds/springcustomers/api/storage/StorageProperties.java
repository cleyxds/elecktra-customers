package net.cleyxds.springcustomers.api.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@ConfigurationProperties("storage")
@Configuration
public class StorageProperties {

	//Folder location for storing files
	private String location = "images";

}
