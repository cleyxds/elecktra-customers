package com.cleyxds.springcustomers.api.filters;

import com.cleyxds.springcustomers.api.utils.JWTUtil;
import com.cleyxds.springcustomers.api.services.CustomerDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

  @Autowired
  private CustomerDetail customerDetail;

  @Autowired
  private JWTUtil JWTTool;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain next)
          throws ServletException, IOException {
    final var authorizationHeader = request.getHeader("Authorization");

    String username = null;
    String jwt = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      username = JWTTool.extractUsername(jwt);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      var customerDetails = customerDetail.loadUserByUsername(username);

      if (JWTTool.validateToken(jwt, customerDetails)) {
        var userPassAuthenticationToken = new UsernamePasswordAuthenticationToken(
          customerDetails,
          null,
          customerDetails.getAuthorities()
        );

        userPassAuthenticationToken.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(userPassAuthenticationToken);
      }
    }
    next.doFilter(request, response);
  }
}
