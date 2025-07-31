package fr.hb.mlang.projectrestapi.config;

import fr.hb.mlang.projectrestapi.entity.Expense;
import fr.hb.mlang.projectrestapi.entity.ExpenseShare;
import fr.hb.mlang.projectrestapi.entity.Group;
import fr.hb.mlang.projectrestapi.entity.Settlement;
import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.repository.ExpenseRepository;
import fr.hb.mlang.projectrestapi.repository.ExpenseShareRepository;
import fr.hb.mlang.projectrestapi.repository.GroupRepository;
import fr.hb.mlang.projectrestapi.repository.SettlementRepository;
import fr.hb.mlang.projectrestapi.repository.UserRepository;
import java.io.InvalidObjectException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev"})
public class DataLoader implements CommandLineRunner {

  @Value("${app.data.loader.generate}")
  private boolean shouldGenerateData;

  private final Logger logger = LoggerFactory.getLogger(DataLoader.class);
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final GroupRepository groupRepository;
  private final ExpenseRepository expenseRepository;
  private final ExpenseShareRepository expenseShareRepository;
  private final SettlementRepository settlementRepository;

  public DataLoader(
      PasswordEncoder passwordEncoder,
      UserRepository userRepository,
      GroupRepository groupRepository,
      ExpenseRepository expenseRepository,
      ExpenseShareRepository expenseShareRepository,
      SettlementRepository settlementRepository
  ) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.groupRepository = groupRepository;
    this.expenseRepository = expenseRepository;
    this.expenseShareRepository = expenseShareRepository;
    this.settlementRepository = settlementRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    if (!shouldGenerateData) {
      return;
    }

    if (userRepository.count() == 0) {
      try {
      //--- Generate Users

      User arthur = userRepository.save(new User(null,"Arthur","arthur@test.com",passwordEncoder.encode("arthur@password")));
      User lancelot = userRepository.save(new User(null,"Lancelot","lancelot@test.com",passwordEncoder.encode("lancelot@password")));
      User leodagan = userRepository.save(new User(null, "Léodagan", null, null));
      User bohort = userRepository.save(new User(null, "Bohort", "bohort@test.com", passwordEncoder.encode("bohort@password")));
      User perceval = userRepository.save(new User(null, "Perceval", null, null));

      //--- Generate Groups

      Group newGroup1 = new Group(null,"Kaamelott", arthur);
      newGroup1.setMembers(Set.of(arthur, lancelot, leodagan, bohort, perceval));
      Group groupKaamelott = groupRepository.save(newGroup1);

      Group newGroup2 = new Group(null, "Mission", perceval);
      newGroup2.addParticipant(arthur);
      Group groupMission = groupRepository.save(newGroup2);

      //--- Generate Expenses, ExpenseShares & Settlements

      Expense expense1 = expenseRepository.save(new Expense(null,"Château",2000.00, LocalDateTime.now(), arthur, groupKaamelott));
      ExpenseShare expenseShare11 = new ExpenseShare(null, 500.00,false, expense1, lancelot);
      ExpenseShare expenseShare12 = new ExpenseShare(null, 500.00,false, expense1, leodagan);
      expenseShareRepository.saveAll(List.of(expenseShare11, expenseShare12));
      settlementRepository.save(new Settlement(null, 500.00, LocalDateTime.now(), "Remboursement Château", groupKaamelott, lancelot, arthur));
      expenseShare11.setPaid(true);
      expenseShareRepository.save(expenseShare11);

      Expense expense2 = expenseRepository.save(new Expense(null,"Armée",500.00, LocalDateTime.now(), leodagan, groupKaamelott));
      ExpenseShare expenseShare21 = new ExpenseShare(null, 400.00,false, expense2, arthur);
      expenseShareRepository.save(expenseShare21);
      settlementRepository.save(new Settlement(null, 1.00, LocalDateTime.now(), "La valeur de votre armée", groupKaamelott, arthur, leodagan));

      Expense expense3 = expenseRepository.save(new Expense(null,"Taverne",12.80, LocalDateTime.now(), arthur, groupMission));
      ExpenseShare expenseShare31 = new ExpenseShare(null, 2.80,false, expense3, perceval);
      expenseShareRepository.save(expenseShare31);
      settlementRepository.save(new Settlement(null, 0.37, LocalDateTime.now(), "Début remboursement", groupMission, perceval, arthur));

      logger.info("Data generated. Database is ready to use!");
      } catch (Exception e) {
        logger.info("Database generation error");
        throw new InvalidObjectException("An error occurred while trying to persist data in the database: " + e.getMessage());
      }

    } else {
      logger.info("Database already contains data. Skipping data generation.");
    }
  }
}
