package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextDumperTest {

  @Test
  void airlineNameIsDumpedInTextFormat() {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(airline);

    String text = sw.toString();
    assertThat(text, containsString(airlineName));
  }

  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    try{
      String airlineName = "Test Airline";
      Airline airline = new Airline(airlineName);

      File textFile = new File(tempDir, "airline.txt");
      TextDumper dumper = new TextDumper(new FileWriter(textFile));
      dumper.dump(airline);

      TextParser parser = new TextParser(new FileReader(textFile));
      Airline read = parser.parse();
    } catch (Exception e){
      assertThat(e.getMessage(), equalTo("Missing flight number"));
    }


  }

  @Test
  void airlineNameIsDumpedInTextFormatToFile() {
    String airlineName = "Emirates";
    Airline airline = new Airline(airlineName);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(airline);

    String text = sw.toString();
    assertThat(text, containsString(airlineName));
  }

  @Test
  void testingFileCreation() throws FileNotFoundException {
    String fileName = "D:\\testing.txt";
    Airline airline = new Airline("JFK");
    Writer w = new PrintWriter(fileName);
    TextDumper textDumper = new TextDumper(w);
    textDumper.dump(airline);
  }

  @Test
  void creatingFileInResources() throws FileNotFoundException {
    String airlineName = "Test Airline";
    String fileName = "src/test/resources/edu/pdx/cs410J/sjagtap/testing1.txt";
    Airline airline = new Airline(airlineName);
    Writer w = new PrintWriter(fileName);
    TextDumper textDumper = new TextDumper(w);
    textDumper.dump(airline);
  }
}
