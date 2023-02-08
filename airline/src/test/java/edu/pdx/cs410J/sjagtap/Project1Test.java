package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project1Test {

  /**
   * Validate airline name.
   */
  @Test
  void validateMain() {
    String[] args = new String[] {
            "-README",
    };
    Project1.main(args);
  }

  /**
   * Validate airline -print
   */
  @Test
  void validateMainPrint() {
    String[] args = new String[] {
            "-print",
            "Indigo",
            "70",
            "SCL",
            "10/10/2023",
            "1:30",
            "SNN",
            "10/10/2023",
            "2:30",
    };
    Project1.main(args);
  }

  /**
   * Validate resource fetch.
   */
  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project1.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("This is a README file!"));

    }
  }

  /**
   * Validate airline name.
   */
  @Test
  void validateMainNoArgs() {
    String[] args = new String[0];
    Project1.main(args);
  }
}
