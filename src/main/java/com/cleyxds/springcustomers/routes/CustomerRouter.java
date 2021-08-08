package com.cleyxds.springcustomers.routes;

import com.cleyxds.springcustomers.handlers.AuthHander;
import com.cleyxds.springcustomers.handlers.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CustomerRouter {

  @Bean
  public RouterFunction<ServerResponse> customersRouter(CustomerHandler customerHandler, AuthHander authHander) {
    return (
      RouterFunctions.route()
        .POST("/customers/token", authHander::getToken)
        .POST("/customers", customerHandler::create)
        .GET("/customers/{id}", customerHandler::fetch)
        .GET("/customers", customerHandler::list)
        .PUT("/customers/{id}", customerHandler::update)
        .DELETE("/customers/{id}", customerHandler::delete)
        .build()
    );
  }

}
