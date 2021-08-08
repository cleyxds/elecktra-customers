package com.cleyxds.springcustomers.repos;

import com.cleyxds.springcustomers.models.CustomerImage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ImageRepo extends ReactiveMongoRepository<CustomerImage, String> {
}
