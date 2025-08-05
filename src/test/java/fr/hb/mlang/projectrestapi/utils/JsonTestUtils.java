package fr.hb.mlang.projectrestapi.utils;

import java.io.IOException;
import java.nio.file.Files;
import org.springframework.core.io.ClassPathResource;

/**
 * Util class that contains methods to read and assert JSON files in testing environment.
 */
public class JsonTestUtils {

  private JsonTestUtils() {
    // prevents instanciation
  }

  /**
   * Finds the JSON file and returns a String to be asserted. Removes ".json" if found at the end of
   * the name (set by default in this method to avoid repetition).
   *
   * @param folderName Name of the folder corresponding to the entity name (ex: entity "Group" ->
   *                   folder "group")
   * @param fileName   Name of the file without ".json" at the end
   * @return A string containing the JSON data ready for assertion.
   * @throws IOException if the file cannot be read.
   */
  public static String readJsonFile(String folderName, String fileName) throws IOException {
    String file = fileName;

    if (fileName.endsWith(".json")) {
      file = fileName.substring(0, fileName.length() - 4);
    }

    ClassPathResource resource = new ClassPathResource("json/" + folderName + "/" + file + ".json");
    return Files.readString(resource.getFile().toPath());
  }

  public static String readJsonFileGroup(String fileName) throws IOException {
    return readJsonFile("groups", fileName);
  }

  public static String readJsonFileExpense(String fileName) throws IOException {
    return readJsonFile("expenses", fileName);
  }
}
