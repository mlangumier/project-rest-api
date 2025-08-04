package fr.hb.mlang.projectrestapi.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final JwtKeyManager jwtKeyManager;
  private final UserDetailsService userDetailsService;

  public JwtService(JwtKeyManager jwtKeyManager, UserDetailsService userDetailsService) {
    this.jwtKeyManager = jwtKeyManager;
    this.userDetailsService = userDetailsService;
  }

  public UserDetails validateToken(String token) {
    String userEmail = this.getUserEmailFromToken(token);
    return userDetailsService.loadUserByUsername(userEmail);
  }

  public String getUserEmailFromToken(String token) {
    try {
      DecodedJWT jwt = JWT.require(jwtKeyManager.getAlgorithm()).build().verify(token);
      return jwt.getSubject();
    } catch (Exception e) {
      throw new RuntimeException("Couldn't decoded JWT: " + e.getMessage());
    }
  }

  // Short expiration for testing purposes
  public String generateToken(String email) {
    return JWT
        .create()
        .withSubject(email)
        .withExpiresAt(Instant.now().plus(5, ChronoUnit.MINUTES))
        .sign(jwtKeyManager.getAlgorithm());
  }
}
