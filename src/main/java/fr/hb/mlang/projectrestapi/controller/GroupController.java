package fr.hb.mlang.projectrestapi.controller;

import fr.hb.mlang.projectrestapi.entity.dto.group.CreateGroupRequest;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponse;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponseLight;
import fr.hb.mlang.projectrestapi.service.GroupService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

  private final GroupService groupService;

  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @GetMapping
  public ResponseEntity<List<GroupResponseLight>> getAllGroups() {
    List<GroupResponseLight> groups = groupService.findAll();
    return ResponseEntity.ok(groups);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GroupResponse> getGroup(@PathVariable(name = "id") UUID groupId) {
    GroupResponse group = groupService.findById(groupId);
    return ResponseEntity.ok(group);
  }

  @PostMapping
  public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody CreateGroupRequest group) {
    GroupResponse response = groupService.create(group);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
