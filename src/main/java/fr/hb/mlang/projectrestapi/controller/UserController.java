package fr.hb.mlang.projectrestapi.controller;

import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponseLight;
import fr.hb.mlang.projectrestapi.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping("/groups")
  public ResponseEntity<List<GroupResponseLight>> getUserGroups(@AuthenticationPrincipal User user) {
    List<GroupResponseLight> response = service.getUserGroups(user.getId());
    return ResponseEntity.ok(response);
  }
}
