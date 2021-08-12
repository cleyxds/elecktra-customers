package net.cleyxds.springcustomers.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtil {

  @Value("${SECRET_KEY:secret}")
  private String SECRET_KEY;

  public String extractUsername(String token) {
    Map<String, String> claims = new HashMap<>();
    var claim = extractClaim(token, Claims::getSubject);
    String[] pairs = claim.split(", ");

    for (String pair : pairs) {
      String[] keyValue = pair.split("=");
      claims.put(keyValue[0], keyValue[1]);
    }
    var email = claims.get("email").replace("}", "");

    return email;
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername(), userDetails.getPassword());
  }

  private String createToken(Map<String, Object> claims, String email, String pass) {
    var sub = new HashMap<String, String>();
    sub.put("email", email);
    sub.put("secret", pass);
    return Jwts.builder().setClaims(claims).claim("sub", sub).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
