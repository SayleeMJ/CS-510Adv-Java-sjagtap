package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import static edu.pdx.cs410J.sjagtap.Options.createAndValidateFlight;

/**
 * A implementation of the <code>XmlParser</code> class for Project 4.
 */
public class XmlParser implements AirlineParser<Airline> {
    private final InputStream reader;

    /**
     * Constructor for XmlParser.
     *
     * @param reader object pointing to source of data.
     */
    public XmlParser(InputStream reader) {
        this.reader = reader;
    }

    /**
     * Parse data from file and create airline object.
     *
     * @return Airline object with information read from xml file.
     */
    @Override
    public Airline parse() throws ParserException {
        // Copied from unit tests.
        AirlineXmlHelper helper = new AirlineXmlHelper();

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        factory.setValidating(true);

        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        builder.setErrorHandler(helper);
        builder.setEntityResolver(helper);

        Document doc = null;
        try {
            doc = builder.parse(reader);
            doc.getDocumentElement().normalize();
        } catch (SAXException | IOException e) {
            throw new ParserException("File is not in required DTD format." + e.getMessage());
        }

        System.out.println("Root = " + doc.getDocumentElement().getNodeName());
        String airlineName = doc.getElementsByTagName("name").item(0).getTextContent();
        Airline airline = new Airline(airlineName);
        NodeList flightNodeList = doc.getElementsByTagName("flight");

        for (int i = 0; i < flightNodeList.getLength(); i++) {
            Node node = flightNodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Flight flight = CreateFlight(node);
                airline.addFlight(flight);
            }
        }

        // New code to read from xml and create required object.
        return airline;
    }

    /**
     * Parse data from file and create flight object.
     *
     * @return Flight object with information read from xml file.
     */
    private Flight CreateFlight(Node node) {
        Element element = (Element) node;
        String flightNumber = element.getElementsByTagName("number").item(0).getTextContent();
        String src = element.getElementsByTagName("src").item(0).getTextContent();

        // Create departure date in required format.
        Node dupartureDateNodeMap = element.getElementsByTagName("depart").item(0);
        String departureDateTime = getDate(dupartureDateNodeMap);

        // Create arrival date in required format.
        String dest = element.getElementsByTagName("dest").item(0).getTextContent();

        Node arrivalDateNodeMap = element.getElementsByTagName("arrive").item(0);
        String arrivalDateTime = getDate(arrivalDateNodeMap);

        Flight flight = createAndValidateFlight(flightNumber, src, departureDateTime, dest, arrivalDateTime);
        return flight;
    }

    /**
     * Parse data from file and gives date string.
     *
     * @return Date time string in required format read from xml file.
     */
    private String getDate(Node dateNode) {
        Element element = (Element) dateNode;
        NamedNodeMap date = element.getElementsByTagName("date").item(0).getAttributes();
        String day = date.getNamedItem("day").getNodeValue();
        String month = date.getNamedItem("month").getNodeValue();
        String year = date.getNamedItem("year").getNodeValue();

        NamedNodeMap time = element.getElementsByTagName("time").item(0).getAttributes();
        String hour = time.getNamedItem("hour").getNodeValue();
        String minute = time.getNamedItem("minute").getNodeValue();

        String datetime = month + "/" + day + "/" + year +  " " + hour + ":" + minute;
        return datetime;
    }
}
