package fr.hb.mlang.projectrestapi.controller;

import fr.hb.mlang.projectrestapi.config.database.DatabaseService;
import java.nio.file.Files;
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
class GroupControllerFunctionalTest {

  @Value("classpath:json/group/create-request.json")
  private Resource groupCreateRequest;
  @Value("classpath:json/group/create-response.json")
  private Resource groupCreateResponse;
  @Value("classpath:json/group/create-request-fail-no-owner.json")
  private Resource groupCreateRequestFail;

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
    String requestJson = Files.readString(groupCreateRequest.getFile().toPath());
    String expectedResponse = Files.readString(groupCreateResponse.getFile().toPath());

    String response = mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/v1/groups")
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
  void givenCreateGroupRequest_whenNoGroupOwner_thenCreateGroupShouldFail() throws Exception {
    String wrongRequest = Files.readString(groupCreateRequestFail.getFile().toPath());

    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/v1/groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(wrongRequest))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
