//package edu.pdx.cs410J.sjagtap;
//
//import edu.pdx.cs410J.ParserException;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//
//import java.io.*;
//import java.util.Map;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.containsString;
//import static org.hamcrest.Matchers.equalTo;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class TextDumperTest {
//
//    @Test
//    void airlineNameIsDumpedInTextFormat() {
//        String airlineName = "Test Airline";
//        Airline airline = new Airline(airlineName);
//
//        StringWriter sw = new StringWriter();
//        TextDumper dumper = new TextDumper(sw);
//        dumper.dump((Map<String, String>) airline);
//
//        String text = sw.toString();
//        assertThat(text, containsString(airlineName));
//    }
//
//    @Test
//    void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
//        try {
//            String airlineName = "Test Airline";
//            Airline airline = new Airline(airlineName);
//            Flight flight1 = Options.createAndValidateFlight(
//                    "3", "SNN", "01/01/2022 10:10", "BJX", "01/01/2022 11:10");
//            airline.addFlight(flight1);
//            File textFile = new File(tempDir, "airline.txt");
//            TextDumper dumper = new TextDumper(new FileWriter(textFile));
//            dumper.dump((Map<String, String>) airline);
//
//            TextParser parser = new TextParser(new FileReader(textFile));
//            Airline read = (Airline) parser.parse();
//        } catch (Exception e) {
//            assertThat(e.getMessage(), equalTo("Missing flight number"));
//        }
//    }
//
//    @Test
//    void airlineNameIsDumpedInTextFormatToFile() {
//        String airlineName = "Emirates";
//        Airline airline = new Airline(airlineName);
//
//        StringWriter sw = new StringWriter();
//        TextDumper dumper = new TextDumper(sw);
//        dumper.dump((Map<String, String>) airline);
//
//        String text = sw.toString();
//        assertThat(text, containsString(airlineName));
//    }
//
//    @Test
//    void testingFileCreation() throws FileNotFoundException {
//        String fileName = "D:\\testing.txt";
//        Airline airline = new Airline("JFK");
//        Writer w = new PrintWriter(fileName);
//        TextDumper textDumper = new TextDumper(w);
//        textDumper.dump((Map<String, String>) airline);
//    }
//
//    @Test
//    void creatingFileInResources() throws FileNotFoundException {
//        String airlineName = "Test Airline";
//        String fileName = "src/test/resources/edu/pdx/cs410J/sjagtap/testing1.txt";
//        Airline airline = new Airline(airlineName);
//        Writer w = new PrintWriter(fileName);
//        TextDumper textDumper = new TextDumper(w);
//        textDumper.dump((Map<String, String>) airline);
//    }
//}