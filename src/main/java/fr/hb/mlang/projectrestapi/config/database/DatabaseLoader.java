package fr.hb.mlang.projectrestapi.config.database;

import fr.hb.mlang.projectrestapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class DatabaseLoader implements ApplicationRunner {

  private final DatabaseService dbService;
  private final UserRepository userRepository;
  private final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

  public DatabaseLoader(DatabaseService dbService, UserRepository userRepository) {
    this.dbService = dbService;
    this.userRepository = userRepository;
  }

  @Override
  public void run(ApplicationArguments args) {
    if (userRepository.count() == 0) {
      dbService.loadDefaultData();
      log.info("Data generated. Database ready to use!");
    } else {
      log.info("Database already contains some data. Skipping data generation.");
    }
  }
}