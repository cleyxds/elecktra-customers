package com.cleyxds.springcustomers.api.controllers;

import com.cleyxds.springcustomers.api.dtos.AuthResponseDTO;
import com.cleyxds.springcustomers.api.dtos.CustomerDTO;
import com.cleyxds.springcustomers.api.utils.JWTUtil;
import com.cleyxds.springcustomers.domain.models.AuthRequest;
import com.cleyxds.springcustomers.api.services.CustomerDetail;
import com.cleyxds.springcustomers.domain.services.interfaces.CustomerServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers/token")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomerServiceRepo customerService;

  @Autowired
  private CustomerDetail customerDetail;

  @Autowired
  private JWTUtil JWTTool;

  @PostMapping
  public ResponseEntity<AuthResponseDTO> createToken(
    @RequestBody AuthRequest authRequest) throws Exception {

    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          authRequest.getEmail(), authRequest.getPassword()));
    } catch (BadCredentialsException e) {
      throw new Exception("Incorrect username and password", e);
    }

    final var customerDetails = customerDetail.loadUserByUsername(
      authRequest.getEmail());

    var customer = new CustomerDTO(customerService.fetchByEmail(customerDetails.getUsername()));

    customerService.attachAvatarUrl(customer);

    final String jwt = JWTTool.generateToken(customerDetails);

    return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDTO(jwt, customer));
  }

  @GetMapping
  public ResponseEntity<CustomerDTO> revalidateCustomer(
    @RequestHeader String jwt) {
    var email = JWTTool.extractUsername(jwt);
    var customer = new CustomerDTO(customerService.fetchByEmail(email));

    customerService.attachAvatarUrl(customer);

    return ResponseEntity.status(HttpStatus.OK).body(customer);
  }
}
