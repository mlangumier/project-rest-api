package fr.hb.mlang.projectrestapi.controller;

import fr.hb.mlang.projectrestapi.config.database.DatabaseService;
import fr.hb.mlang.projectrestapi.utils.JsonTestUtils;
import fr.hb.mlang.projectrestapi.utils.MockTestUtils;
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
class GroupControllerFunctionalTest {

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
  void givenCreateGroupRequest_whenGoodRequest_thenCreateGroupShouldSucceed() throws Exception {
    String requestJson = JsonTestUtils.readJsonFileGroup("createGroup_valid");
    String expectedResponse = JsonTestUtils.readJsonFileGroup("createGroup_expected");

    String response = MockTestUtils
        .performPost(mockMvc, "/api/v1/groups", requestJson)
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    JSONAssert.assertEquals(expectedResponse, response, MockTestUtils.ignoreJsonIds());
  }

  @Test
  void givenCreateGroupRequest_whenNoGroupOwner_thenCreateGroupShouldFail() throws Exception {
    String invalidRequest = JsonTestUtils.readJsonFileGroup("createGroup_invalid_noOwner");

    MockTestUtils
        .performPost(mockMvc, "/api/v1/groups", invalidRequest)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

  }
}
