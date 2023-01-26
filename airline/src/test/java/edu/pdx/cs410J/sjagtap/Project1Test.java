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

  @Test
  void validateSourceAndDestination(){
    assertThat(Project1.isValidSrcAndDest("src"), is(true));
    assertThat(Project1.isValidSrcAndDest("source"), is(false));
  }

  @Test
  void validateSourceAndDestinationNumbers(){
    assertThat(Project1.isValidSrcAndDest("s12"), is(false));
    assertThat(Project1.isValidSrcAndDest("s$12"), is(false));
  }

  @Test
  void validateOption(){
    assertThat(Project1.isValidOption("-print"), is(true));
    assertThat(Project1.isValidOption("-README"), is(true));
  }

  @Test
  void notValidateOption(){
    assertThat(Project1.isValidOption("-abcd"), is(false));
    assertThat(Project1.isValidOption("-efgh"), is(false));
  }

  @Test
  void getValidHelpMessage(){
      assertThat(Project1.getHelpMessage(), not(nullValue()));
      assertThat(Project1.getHelpMessage(), containsString("usage: java -jar target/airline-2023.0.0.jar [options] <args>"));
  }

  @Test
  void getFlightNumber(){
    assertThat(Project1.isValidFlightNumber("12"), is(true));
    assertThat(Project1.isValidFlightNumber("1A"), is(false));
  }
}
