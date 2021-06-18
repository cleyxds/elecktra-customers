package net.cleyxds.sqlite.api.controller;

import net.cleyxds.sqlite.api.exception.StorageFileNotFoundException;
import net.cleyxds.sqlite.domain.repository.ImageRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
  private ImageRepositoryService imageRepositoryService;

  @GetMapping
  public ResponseEntity<List<URI>> listOfImages() {
    List<URI> images;

    images = imageRepositoryService.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(ImageUploadController.class,
                    "fetchImage", path.getFileName().toString()).build().toUri())
            .collect(Collectors.toList());

    return ResponseEntity.ok().body(images);
  }

  @GetMapping("/files/{filename:.+}")
  public ResponseEntity<Resource> fetchImage(@PathVariable String filename) {
    Resource file = imageRepositoryService.loadAsResource(filename);

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + file.getFilename() + "\"")
            .body(file);
  }

  @PostMapping("/{id}")
  public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
    imageRepositoryService.store(file, id);

    return ResponseEntity.created(null).build();
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }

}
