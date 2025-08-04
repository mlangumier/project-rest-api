package fr.hb.mlang.projectrestapi.service.impl;

import fr.hb.mlang.projectrestapi.config.security.jwt.JwtService;
import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.entity.dto.UserMapper;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserLoginRequest;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserLoginResponse;
import fr.hb.mlang.projectrestapi.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final JwtService jwtService;
  private final AuthenticationManager authManager;
  private final UserMapper mapper;

  public AuthenticationServiceImpl(
      JwtService jwtService,
      UserMapper mapper,
      AuthenticationManager authManager
  ) {
    this.jwtService = jwtService;
    this.authManager = authManager;
    this.mapper = mapper;
  }

  @Override
  public UserLoginResponse login(UserLoginRequest request) {
    Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.username(),
        request.password()
    ));
    User user = (User) authentication.getPrincipal();
    String token = jwtService.generateToken(user.getEmail());

    return new UserLoginResponse(mapper.entityToUserDto(user), token);
  }
}
