package fr.hb.mlang.projectrestapi.controller;

import fr.hb.mlang.projectrestapi.config.database.DatabaseService;
import fr.hb.mlang.projectrestapi.repository.GroupRepository;
import fr.hb.mlang.projectrestapi.utils.JsonTestUtils;
import fr.hb.mlang.projectrestapi.utils.MockTestUtils;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerFunctionalTest {

  @Autowired
  private DatabaseService databaseService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private GroupRepository groupRepository;

  private UUID groupId;

  @BeforeEach
  void setUp() {
    databaseService.loadDefaultData();

    groupId = groupRepository
        .findByNameIgnoreCase("kaamelott")
        .orElseThrow(() -> new EntityNotFoundException("Group not found"))
        .getId();
  }

  @AfterEach
  void tearDown() {
    databaseService.deleteData();
  }

  @Test
  void givenCreateExpenseRequest_whenGoodRequest_thenCreateExpenseShouldSucceed() throws Exception {
    String requestJson = JsonTestUtils.readJsonFileExpense("createExpense_valid");
    String expectedResponse = JsonTestUtils.readJsonFileExpense("createExpense_expected");

    String response = MockTestUtils
        .performPost(mockMvc, "/api/v1/groups/" + groupId + "/expenses", requestJson)
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    JSONAssert.assertEquals(expectedResponse, response, MockTestUtils.ignoreJsonIds());
  }

  @Test
  void givenCreateGroupRequest_whenEmptyPaidForUsersArray_thenCreateExpenseShouldFail()
      throws Exception {
    String invalidRequest = JsonTestUtils.readJsonFileExpense("createExpense_invalid_noDebtors");

    MockTestUtils
        .performPost(mockMvc, "/api/v1/groups/" + groupId + "/expenses", invalidRequest)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void givenCreateGroupRequest_whenEmptyAmount_thenCreateExpenseShouldFail() throws Exception {
    String invalidRequest = JsonTestUtils.readJsonFileExpense("createExpense_invalid_noAmount");

    MockTestUtils
        .performPost(mockMvc, "/api/v1/groups/" + groupId + "/expenses", invalidRequest)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
