package fr.hb.mlang.projectrestapi.config;

import fr.hb.mlang.projectrestapi.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {

  private final UserRepository userRepository;

  public ApplicationConfig(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository
        .findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(
            "Couldn't find user with email: " + username));
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
