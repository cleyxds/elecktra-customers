package com.cleyxds.springcustomers.api.controllers;

import com.cleyxds.springcustomers.api.exceptions.StorageFileNotFoundException;
import com.cleyxds.springcustomers.api.exceptions.StorageException;
import com.cleyxds.springcustomers.domain.services.interfaces.ImageServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ImageController {

  @Autowired
  private ImageServiceRepo imageService;

  @GetMapping("/api/images")
  public ResponseEntity<List<URI>> list() {
    var images_url = imageService.loadAll()
      .map(path ->
        MvcUriComponentsBuilder.fromMethodName(
          ImageController.class, "serveImageUrl", path.getFileName().toString()
        )
        .build().toUri())
      .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(images_url);
  }

  @PostMapping("/api/images")
  public ResponseEntity<URI> create(@RequestParam("file") MultipartFile file, @RequestHeader Long id) {
    imageService.store(file, id);
    var URI =  imageService.loadImageById(id);

    return ResponseEntity.status(HttpStatus.CREATED).body(URI);
  }

  @GetMapping("/api/images/customers")
  public ResponseEntity<URI> fetch(@RequestHeader Long id) {
    var image_url = imageService.loadImageById(id);

    return ResponseEntity.status(HttpStatus.CREATED).body(image_url);
  }

  @GetMapping("/images/{filename:.+}")
  public ResponseEntity<?> serveImageUrl(@PathVariable String filename) {
    var image_resource = imageService.loadAsResource(filename);
    return (
      ResponseEntity
        .status(HttpStatus.OK)
        .header(HttpHeaders.CONTENT_TYPE, "image/png", "image/jpeg")
        .body(image_resource)
    );
  }

  @ExceptionHandler({StorageFileNotFoundException.class, StorageException.class})
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc, StorageException exc2) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

}
