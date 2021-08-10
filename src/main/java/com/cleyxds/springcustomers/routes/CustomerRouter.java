package com.cleyxds.springcustomers.routes;

import com.cleyxds.springcustomers.handlers.AuthHander;
import com.cleyxds.springcustomers.handlers.CustomerHandler;
import com.cleyxds.springcustomers.handlers.ImageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CustomerRouter {

  @Bean
  public RouterFunction<ServerResponse> customersRouter(
    CustomerHandler customerHandler,
    AuthHander authHander,
    ImageHandler imageHandler
  ) {
    return (
      RouterFunctions.route()
        .POST("/customers/token", authHander::getToken)
        .POST("/customers", customerHandler::create)
        .POST("/customers/image",
          RequestPredicates.accept(MediaType.MULTIPART_FORM_DATA),
          imageHandler::upload)
        .GET("/customers/{id}", customerHandler::fetch)
        .GET("/customers", customerHandler::list)
        .PUT("/customers/{id}", customerHandler::update)
        .DELETE("/customers/{id}", customerHandler::delete)
        .build()
    );
  }

}
