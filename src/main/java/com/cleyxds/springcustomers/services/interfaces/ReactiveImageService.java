package com.cleyxds.springcustomers.services.interfaces;

import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface ReactiveImageService {

  Path imagesFolder = Paths.get("images");

  Boolean upload(String id, MultiValueMap<String, Part> parts);

}
