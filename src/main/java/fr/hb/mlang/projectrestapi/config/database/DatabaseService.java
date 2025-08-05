package fr.hb.mlang.projectrestapi.config.database;

import fr.hb.mlang.projectrestapi.entity.Expense;
import fr.hb.mlang.projectrestapi.entity.ExpenseShare;
import fr.hb.mlang.projectrestapi.entity.Group;
import fr.hb.mlang.projectrestapi.entity.Settlement;
import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.repository.ExpenseRepository;
import fr.hb.mlang.projectrestapi.repository.GroupRepository;
import fr.hb.mlang.projectrestapi.repository.SettlementRepository;
import fr.hb.mlang.projectrestapi.repository.UserRepository;
import fr.hb.mlang.projectrestapi.utils.MoneyUtils;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("!prod")
public class DatabaseService {

  private final UserRepository userRepository;
  private final GroupRepository groupRepository;
  private final ExpenseRepository expenseRepository;
  private final SettlementRepository settlementRepository;
  private final PasswordEncoder encoder;

  public DatabaseService(
      UserRepository userRepository,
      GroupRepository groupRepository,
      ExpenseRepository expenseRepository,
      SettlementRepository settlementRepository,
      PasswordEncoder encoder
  ) {
    this.userRepository = userRepository;
    this.groupRepository = groupRepository;
    this.expenseRepository = expenseRepository;
    this.settlementRepository = settlementRepository;
    this.encoder = encoder;
  }

  @Transactional
  public void loadDefaultData() {
    //--- Users
    User arthur = new User(null,"Arthur","arthur@test.com", encoder.encode("arthur"));
    User lancelot = new User(null, "Lancelot", "lancelot@test.com", encoder.encode("lancelot"));
    User bohort = new User(null, "Bohort", "bohort@test.com", encoder.encode("bohort"));
    User leodagan = new User(null, "Léodagan", null, null);
    User perceval = new User(null, "Perceval", null, null);
    userRepository.saveAll(List.of(arthur, lancelot, bohort, leodagan, perceval));

    //--- Groups
    Group groupKaamelott = new Group(null, "Kaamelott", arthur);
    groupKaamelott.setMembers(Set.of(arthur, lancelot, leodagan, bohort, perceval));
    Group groupMission = new Group(null, "Mission", perceval);
    groupMission.setMembers(Set.of(perceval, arthur));
    groupRepository.saveAll(List.of(groupKaamelott, groupMission));

    //--- Group "Kaamelott" - Expenses (2), Shares (4)
    Expense constructionChateau = new Expense(null, "Construction château", MoneyUtils.of(2000), arthur, groupKaamelott);
    ExpenseShare chateauShareArthur = new ExpenseShare(null, MoneyUtils.of(1000), true, constructionChateau, arthur);
    ExpenseShare chateauShareLancelot = new ExpenseShare(null, MoneyUtils.of(500), true, constructionChateau, lancelot);
    ExpenseShare chateauShareLeodagan = new ExpenseShare(null, MoneyUtils.of(500), false, constructionChateau, leodagan);
    constructionChateau.setExpenseShares(Set.of(chateauShareArthur, chateauShareLancelot, chateauShareLeodagan));
    expenseRepository.save(constructionChateau);
    Settlement lancelotSettlement = new Settlement(null, MoneyUtils.of(500), "Remboursement Château", groupKaamelott, lancelot, arthur);
    settlementRepository.save(lancelotSettlement);

    Expense armeeDefense = new Expense(null, "Défense contre les Burgondes", MoneyUtils.of(500), leodagan, groupKaamelott);
    ExpenseShare armeeShareLeodagan = new ExpenseShare(null, MoneyUtils.of(100), true, armeeDefense, arthur);
    ExpenseShare armeeShareArthur = new ExpenseShare(null, MoneyUtils.of(400), true, armeeDefense, arthur);
    armeeDefense.setExpenseShares(Set.of(armeeShareLeodagan, armeeShareArthur));
    expenseRepository.save(armeeDefense);
    Settlement arthurSettlement = new Settlement(null, MoneyUtils.of(1.00), "La valeur de votre armée", groupKaamelott, arthur, leodagan);
    settlementRepository.save(arthurSettlement);

    //--- Group "Mission" - Expenses, Shares & Settlements
    Expense taverne = new Expense(null, "Nuit à la taverne", MoneyUtils.of(12.80), arthur, groupMission);
    ExpenseShare taverneShareArthur = new ExpenseShare(null, MoneyUtils.of(10), true, taverne, arthur);
    ExpenseShare taverneSharePerceval = new ExpenseShare(null, MoneyUtils.of(2.80), false, taverne, perceval);
    taverne.setExpenseShares(Set.of(taverneShareArthur, taverneSharePerceval));
    expenseRepository.save(taverne);
    Settlement percevalSettlement = new Settlement(null, MoneyUtils.of(0.37), "Début remb. taverne", groupMission, perceval, arthur);
    settlementRepository.save(percevalSettlement);
  }

  @Transactional
  public void deleteData() {
    expenseRepository.deleteAll();
    expenseRepository.flush();

    groupRepository.deleteAll();
    groupRepository.flush();

    userRepository.deleteAll();
    userRepository.flush();
  }

  @Transactional
  public void resetDatabase() {
    this.deleteData();
    this.loadDefaultData();
  }
}
