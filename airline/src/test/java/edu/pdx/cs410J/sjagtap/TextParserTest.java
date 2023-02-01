package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {

  @Test
  void validTextFileCanBeParsed()  {
    try {
      InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
      assertThat(resource, notNullValue());

      TextParser parser = new TextParser(new InputStreamReader(resource));
      Airline airline = parser.parse();
    } catch (Exception e){
      assertThat(e.getMessage(), equalTo("Missing flight number"));
    }
  }

  @Test
  void invalidTextFileThrowsParserException() {
    InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }
  @Test
  void readingCreatedFile() throws FileNotFoundException, ParserException {
    String fileName = "D:\\testing.txt";
    Airline airline;
    Reader r = new FileReader(fileName);
    TextParser textParser = new TextParser(r);
    airline = textParser.parse();
    assertThat(airline.getName(), equalTo("Indigo"));
  }

  @Test
  void readingCreatedFileForExistingAirlineName() throws FileNotFoundException, ParserException {
      String fileName = "D:\\testing.txt";
      Airline airline;
      Reader r = new FileReader(fileName);
      TextParser textParser = new TextParser(r);
      airline = textParser.parse();
      String existingAirlineName = airline.getName();
      String newAirlineName = "Indigo";
      assertThat(existingAirlineName,equalTo(newAirlineName));
  }
}
