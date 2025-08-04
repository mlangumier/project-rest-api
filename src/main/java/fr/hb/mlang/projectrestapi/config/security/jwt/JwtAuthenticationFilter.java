package fr.hb.mlang.projectrestapi.config.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  public JwtAuthenticationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authHeader.substring("Bearer ".length());

    try {
      UserDetails userDetails = jwtService.validateToken(token);

      this.authenticateUser(userDetails);

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      throw new AuthenticationServiceException("Failed to authenticated user: " + e.getMessage());
    }
  }

  private void authenticateUser(UserDetails user) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user,
        null,
        user.getAuthorities()
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
