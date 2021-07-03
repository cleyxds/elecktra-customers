package net.cleyxds.springcustomers.api.filter;

import net.cleyxds.springcustomers.api.service.CustomerDetailService;
import net.cleyxds.springcustomers.api.util.JwtUtil;
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
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private CustomerDetailService customerDetailService;

  @Autowired
  private JwtUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain next)
          throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");

    String username = null;
    String jwt = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      username = jwtTokenUtil.extractUsername(jwt);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      var customerDetails = this.customerDetailService.loadUserByUsername(username);

      if (jwtTokenUtil.validateToken(jwt, customerDetails)) {
        var userPassAuthenticationToken = new UsernamePasswordAuthenticationToken(
                customerDetails, null, customerDetails.getAuthorities());

        userPassAuthenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(userPassAuthenticationToken);
      }
    }
    next.doFilter(request, response);
  }
}
