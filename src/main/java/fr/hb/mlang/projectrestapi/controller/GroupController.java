package fr.hb.mlang.projectrestapi.controller;

import fr.hb.mlang.projectrestapi.entity.dto.group.CreateGroupRequest;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponse;
import fr.hb.mlang.projectrestapi.entity.Group;
import fr.hb.mlang.projectrestapi.entity.dto.group.UpdateGroupRequest;
import fr.hb.mlang.projectrestapi.service.GroupService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

  private final GroupService groupService;

  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @GetMapping
  public ResponseEntity<List<Group>> getAllGroups() {
    return ResponseEntity.ok(List.of(new Group()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Group> getGroup(@PathVariable(name = "id") String groupId) {
    // Get group according to ID
    // Record DTO for response "detailed"
    return ResponseEntity.ok(new Group());
  }

  @PostMapping
  public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody CreateGroupRequest group) {
    GroupResponse response = groupService.createGroup(group);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<GroupResponse> updateGroup(@PathVariable(name = "id") UUID groupId, @Valid @RequestBody UpdateGroupRequest group) {
    GroupResponse response = groupService.updateGroup(groupId, group);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Group> deleteGroup(@PathVariable(name = "id") String groupId) {
    // Delete / Archive group
    return ResponseEntity.noContent().build();
  }
}
