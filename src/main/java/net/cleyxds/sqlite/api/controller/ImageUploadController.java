package net.cleyxds.sqlite.api.controller;

import net.cleyxds.sqlite.api.exception.StorageFileNotFoundException;
import net.cleyxds.sqlite.domain.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class ImageUploadController {

  @Autowired
  private ImageService service;

  @GetMapping
  public ResponseEntity<List<URI>> list() {
    List<URI> images = service.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(ImageUploadController.class,
                    "fetchImageResource", path.getFileName().toString()).build().toUri())
            .collect(Collectors.toList());

    return ResponseEntity.ok().body(images);
  }

  @PostMapping("/{id}")
  public ResponseEntity<?> create(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
    service.store(file, id);

    return ResponseEntity.created(null).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<URI> fetch(@PathVariable Long id) {
    URI image = MvcUriComponentsBuilder.fromMethodName(
            ImageUploadController.class,
            "fetchImageResource", service.loadById(id).getFileName().toString())
            .build().toUri();

    return ResponseEntity.ok().body(image);
  }

  @GetMapping("/resource/{filename:.+}")
  public ResponseEntity<Resource> fetchImageResource(@PathVariable String filename) {
    Resource image = service.loadAsResource(filename);
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, "image/png")
            .body(image);
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }

}
