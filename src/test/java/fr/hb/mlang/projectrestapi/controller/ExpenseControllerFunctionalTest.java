package fr.hb.mlang.projectrestapi.controller;

import fr.hb.mlang.projectrestapi.config.database.DatabaseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerFunctionalTest {

  @Value("classpath:json/expense-create-request.json")
  private Resource expenseCreateRequest;
  @Value("classpath:json/expense-create-response.json")
  private Resource expenseCreateResponse;

  @Autowired
  private DatabaseService databaseService;
  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    databaseService.loadDefaultData();
  }

  @AfterEach
  void tearDown() {
    databaseService.deleteData();
  }

  @Test
  void shouldCreateExpenseSuccessfully() throws Exception {
    //Something, something... Vicky... Something, something...
  }

}
