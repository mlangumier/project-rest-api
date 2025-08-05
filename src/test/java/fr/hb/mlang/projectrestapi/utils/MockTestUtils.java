package fr.hb.mlang.projectrestapi.utils;

import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class MockTestUtils {

  public MockTestUtils() {
    // prevents instanciation
  }

  /**
   * Performs a <code>POST</code> request using the url and the jsonRequest file in order to return
   * the result to be checked.
   *
   * @param mockMvc     Class that performs the test request
   * @param url         Url of the request
   * @param jsonRequest Json file with the <code>body</code> content of the request
   * @return the result action ready to be verified
   * @throws Exception if the request fails
   */
  public static ResultActions performPost(MockMvc mockMvc, String url, String jsonRequest)
      throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders
        .post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonRequest));
  }

  /**
   * Removes the IDs from JSON response files to allows proper comparison with the expected
   * response.
   *
   * @return the custom rule for comparison without IDs
   */
  public static CustomComparator ignoreJsonIds() {
    return new CustomComparator(
        JSONCompareMode.LENIENT,
        new Customization("id", (o1, o2) -> true)
    );
  }

}
