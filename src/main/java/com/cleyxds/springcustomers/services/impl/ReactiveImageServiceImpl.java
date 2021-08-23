package com.cleyxds.springcustomers.services.impl;

import com.cleyxds.springcustomers.services.interfaces.ReactiveImageService;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class ReactiveImageServiceImpl implements ReactiveImageService {

  @Override
  public Boolean upload(String id, MultiValueMap<String, Part> parts) {
    var map = parts.toSingleValueMap();
    var uploadedImage = (FilePart) map.get("image");

    var imageId = UUID.randomUUID();
    var ImageFilename = (imageId + "-" + uploadedImage.filename());
    var imagePath = imagesFolder.resolve(ImageFilename);

    uploadedImage.content()
      .cache(8192)
      .subscribe(db -> {
        try {
          Files.copy(db.asInputStream(), imagePath);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

    System.out.println("Grava no banco o (customer.id()|imageId.toString()) = customer.id = " + id);
    return Files.exists(imagePath);
  }

}
