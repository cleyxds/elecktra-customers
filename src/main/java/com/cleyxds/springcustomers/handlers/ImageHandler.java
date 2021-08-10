package com.cleyxds.springcustomers.handlers;

import lombok.SneakyThrows;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ImageHandler {

  private final Path imagesFolder = Paths.get("images");

  @SneakyThrows
  public Mono<ServerResponse> upload(ServerRequest request) {
    return (
      request.body(BodyExtractors.toMultipartData())
        .flatMap(parts -> {
          var map = parts.toSingleValueMap();
          var uploadedImage = (FilePart) map.get("image");

          var filename = uploadedImage.filename();
          filename = (UUID.randomUUID() + "-" + filename);

          var image = new File(
                  imagesFolder.resolve(filename)
                          .toString());

//            image.createNewFile();

          return ServerResponse.status(201).build();
        })
    );
  }
}
