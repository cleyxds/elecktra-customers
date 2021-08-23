package com.cleyxds.springcustomers.handlers;

import com.cleyxds.springcustomers.services.interfaces.ReactiveImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ImageHandler {

  @Autowired
  private ReactiveImageService service;

  public Mono<ServerResponse> upload(ServerRequest request) {
    var id = request.headers().asHttpHeaders().getFirst("id");

    if (id == null)
      return ServerResponse.status(HttpStatus.BAD_REQUEST).build();
    return (
      request.body(BodyExtractors.toMultipartData())
        .map(parts -> service.upload(id, parts))
        .flatMap(result ->
          result ?
            ServerResponse.status(HttpStatus.CREATED).build()
            :
            ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
          )
    );
  }
}
