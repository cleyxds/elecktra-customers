package net.cleyxds.springcustomers.api.controller;

import net.cleyxds.springcustomers.api.exception.StorageException;
import net.cleyxds.springcustomers.api.exception.StorageFileNotFoundException;
import net.cleyxds.springcustomers.domain.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ImageUploadController {

  @Autowired
  private ImageService service;

  @GetMapping("/images")
  public ResponseEntity<List<URI>> list() {
    var images_url = service.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(ImageUploadController.class,
                    "serveImageUrl", path.getFileName().toString()).build().toUri())
            .collect(Collectors.toList());

    return ResponseEntity.ok().body(images_url);
  }

  // ID COMES IN THE REQUEST HEADER
  @PostMapping("/images")
  public ResponseEntity<?> create(@RequestParam("file") MultipartFile file, @RequestHeader Long id) {
    service.store(file, id);

    return ResponseEntity.status(201).build();
  }

  @GetMapping("/images/customers/{id}")
  public ResponseEntity<URI> fetch(@PathVariable Long id) {
    var image_url = service.loadImageById(id);

    return ResponseEntity.ok().body(image_url);
  }

  @GetMapping("/images/{filename:.+}")
  public ResponseEntity<?> serveImageUrl(@PathVariable String filename) {
    var image_resource = service.loadAsResource(filename);
    return (
      ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_TYPE, "image/png", "image/jpeg")
        .body(image_resource)
    );
  }

  @ExceptionHandler({StorageFileNotFoundException.class, StorageException.class})
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc, StorageException exc2) {
    return ResponseEntity.notFound().build();
  }

}
