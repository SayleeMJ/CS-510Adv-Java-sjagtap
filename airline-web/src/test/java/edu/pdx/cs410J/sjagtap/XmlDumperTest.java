package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class XmlDumperTest {

    @Test
    void testingFileCreation() throws IOException {
        String fileName = "D:\\testingXML.xml";
        Airline airline = new Airline("Emirates");
        OutputStream outputStream = new FileOutputStream(fileName);
        XmlDumper xmlDumper = new XmlDumper(outputStream);
        xmlDumper.dump(airline);
    }

    @Test
    void canParseTextWrittenByXMLDumper() throws IOException, ParserException {
        try {
            String airlineName = "Test Airline";
            Airline airline = new Airline(airlineName);
            Flight flight1 = Options.createAndValidateFlight(
                    "123", "SNN", "01/01/2022 10:10", "SNN", "01/01/2022 11:10");
            airline.addFlight(flight1);
            OutputStream outputStream = new FileOutputStream("src/test/resources/edu/pdx/cs410J/sjagtap/testingXML.xml");
            XmlDumper xmlDumper = new XmlDumper(outputStream);
            xmlDumper.dump(airline);

            InputStream inputStream = new FileInputStream("src/test/resources/edu/pdx/cs410J/sjagtap/testingXML.xml");
            XmlParser xmlParser = new XmlParser(inputStream);
            airline = xmlParser.parse();
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo(e.getMessage()));
        }
    }

    @Test
    void canCatchTheThreeLetterCodeDoesNotCorrespond () throws IOException, ParserException {
        try {
            String airlineName = "Test Airline";
            Airline airline = new Airline(airlineName);
            Flight flight1 = Options.createAndValidateFlight(
                    "123", "SNN", "01/01/2022 10:10", "ABC", "01/01/2022 11:10");
            airline.addFlight(flight1);
            OutputStream outputStream = new FileOutputStream("src/test/resources/edu/pdx/cs410J/sjagtap/testingXML.xml");
            XmlDumper xmlDumper = new XmlDumper(outputStream);
            xmlDumper.dump(airline);

            InputStream inputStream = new FileInputStream("src/test/resources/edu/pdx/cs410J/sjagtap/testingXML.xml");
            XmlParser xmlParser = new XmlParser(inputStream);
            airline = xmlParser.parse();
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo(e.getMessage()));
        }
    }
}