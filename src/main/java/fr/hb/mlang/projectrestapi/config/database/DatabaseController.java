package fr.hb.mlang.projectrestapi.config.database;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/database")
@Profile("!prod") // Never use in production
public class DatabaseController {

  private final DatabaseService dbService;

  public DatabaseController(DatabaseService dbService) {
    this.dbService = dbService;
  }

  @PostMapping
  public ResponseEntity<String> generateData() {
    dbService.loadDefaultData();
    return ResponseEntity.ok("Data generated!");
  }

  @PostMapping("/reset")
  public ResponseEntity<String> resetData() {
    dbService.resetDatabase();
    return ResponseEntity.ok("Database reset and populated with default data!");
  }
}