package net.cleyxds.springcustomers.api.controller;

import net.cleyxds.springcustomers.api.dto.AuthenticationResponseDTO;
import net.cleyxds.springcustomers.api.dto.CustomerDTO;
import net.cleyxds.springcustomers.api.service.CustomerDetailService;
import net.cleyxds.springcustomers.api.util.JwtUtil;
import net.cleyxds.springcustomers.domain.model.AuthenticationRequest;
import net.cleyxds.springcustomers.domain.service.CustomerService;
import net.cleyxds.springcustomers.domain.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/customers/token")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private CustomerDetailService customerDetailService;

  @Autowired
  private JwtUtil jwtTokenUtil;

  @PostMapping
  public ResponseEntity<AuthenticationResponseDTO> createToken(
          @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      authenticationRequest.getEmail(), authenticationRequest.getPassword()));
    } catch (BadCredentialsException e) {
      throw new Exception("Incorrect username and password", e);
    }
    final var customerDetails = customerDetailService.loadUserByUsername(
            authenticationRequest.getEmail());

    var customer = new CustomerDTO(customerService.fetchByEmail(customerDetails.getUsername()));

    try {
      customer.setAvatar_url(imageService.loadImageById(customer.getId()).toString());
    } catch (NoSuchElementException e) {
      customer.setAvatar_url(null);
    }

    final String jwt = jwtTokenUtil.generateToken(customerDetails);

    return ResponseEntity.ok(new AuthenticationResponseDTO(jwt, customer));
  }
}
