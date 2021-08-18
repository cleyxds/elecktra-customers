package net.cleyxds.springcustomers.api.controller;

import net.cleyxds.springcustomers.api.dto.AuthenticationResponseDTO;
import net.cleyxds.springcustomers.api.dto.CustomerDTO;
import net.cleyxds.springcustomers.api.service.CustomerDetailService;
import net.cleyxds.springcustomers.api.util.JwtUtil;
import net.cleyxds.springcustomers.domain.model.AuthenticationRequest;
import net.cleyxds.springcustomers.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers/token")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomerService customerService;

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

    customerService.attachAvatarUrl(customer);

    final String jwt = jwtTokenUtil.generateToken(customerDetails);

    return ResponseEntity.ok(new AuthenticationResponseDTO(jwt, customer));
  }

  @GetMapping
  public ResponseEntity<CustomerDTO> revalidateCustomer(
    @RequestHeader String jwt) {
    var email = jwtTokenUtil.extractUsername(jwt);
    var customer = new CustomerDTO(customerService.fetchByEmail(email));

    customerService.attachAvatarUrl(customer);

    return ResponseEntity.ok().body(customer);
  }
}
