package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class XmlParser implements AirlineParser {

    private final InputStream reader;

    /**
     * Constructor for TextParser.
     *
     * @param reader object pointing to source of data.
     */
    public XmlParser(InputStream reader) {
        this.reader = reader;
    }
    @Override
    public Airline parse() throws ParserException {
        AirlineXmlHelper helper = new AirlineXmlHelper();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(true);

        DocumentBuilder builder;

        try {
            builder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw  new RuntimeException(e);
        }
        builder.setErrorHandler(helper);
        builder.setEntityResolver(helper);


        Document document = null;
        try {
            document = builder.parse(reader);
            document.getDocumentElement().normalize();
        } catch (IOException | SAXException e) {
            throw new ParserException("File format is not according to DTD format" + e.getMessage());
        }

        System.out.println("Root =" + document.getDocumentElement().getNodeName());
        String airlineName = document.getElementsByTagName("name").item(0).getTextContent();
        Airline airline = new Airline(airlineName);

        NodeList nodeList = document.getElementsByTagName("flight");

        for(int i =0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Flight flight = createFlight(node);
                airline.addFlight(flight);
            }
        }
        return airline;
    }

    private Flight createFlight(Node node) {
        Element element = (Element) node;
        String flightNumber = element.getElementsByTagName("number").item(0).getTextContent();
        String src = element.getElementsByTagName("src").item(0).getTextContent();

        Node departureTimeDate = element.getElementsByTagName("depart").item(0);
        String departDate = getDateTime(departureTimeDate);

        String dst = element.getElementsByTagName("dest").item(0).getTextContent();

        Node arrivalTimeDate = element.getElementsByTagName("arrive").item(0);
        String arriveDate = getDateTime(arrivalTimeDate);

        Flight flight = Options.createAndValidateFlight(flightNumber, src, departDate, dst, arriveDate);
        return flight;
    }

    private String getDateTime(Node departureTimeDate) {
        Element element = (Element) departureTimeDate;
        NamedNodeMap date = element.getElementsByTagName("date").item(0).getAttributes();
        String day = date.getNamedItem("day").getNodeValue();
        String month = date.getNamedItem("month").getNodeValue();
        String year = date.getNamedItem("year").getNodeValue();

        NamedNodeMap time = element.getElementsByTagName("time").item(0).getAttributes();
        String hour = time.getNamedItem("hour").getNodeValue();
        String minute = time.getNamedItem("minute").getNodeValue();

        String dateAndTime = month + "/" + day + "/" + year +  " " + hour + ":" + minute;
        return dateAndTime;
    }
}
