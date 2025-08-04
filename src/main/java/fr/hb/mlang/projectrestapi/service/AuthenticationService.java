package fr.hb.mlang.projectrestapi.service;

import fr.hb.mlang.projectrestapi.entity.dto.user.UserLoginRequest;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserLoginResponse;

public interface AuthenticationService {

  UserLoginResponse login(UserLoginRequest request);

}
