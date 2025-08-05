package fr.hb.mlang.projectrestapi.controller;

import fr.hb.mlang.projectrestapi.config.database.DatabaseService;
import fr.hb.mlang.projectrestapi.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import java.nio.file.Files;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerFunctionalTest {

  @Value("classpath:json/expense/create-request.json")
  private Resource expenseCreateRequest;
  @Value("classpath:json/expense/create-response.json")
  private Resource expenseCreateResponse;
  @Value("classpath:json/expense/create-request-fail-no-debtors.json")
  private Resource expenseCreateRequestFail;

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
    String requestJson = Files.readString(expenseCreateRequest.getFile().toPath());
    String expectedResponse = Files.readString(expenseCreateResponse.getFile().toPath());

    String response = mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/v1/groups/" + groupId + "/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    JSONAssert.assertEquals(
        expectedResponse,
        response,
        new CustomComparator(JSONCompareMode.LENIENT, new Customization("id", (o1, o2) -> true))
    );
  }

  @Test
  void givenCreateGroupRequest_whenEmptyPaidForUsersArray_thenCreateExpenseShouldFail() throws Exception {
    String wrongRequest = Files.readString(expenseCreateRequestFail.getFile().toPath());

    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/v1/groups/" + groupId + "/expenses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(wrongRequest))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
