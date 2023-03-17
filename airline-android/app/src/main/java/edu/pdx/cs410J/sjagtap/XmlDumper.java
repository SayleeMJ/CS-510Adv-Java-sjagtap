package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class XmlDumper implements AirlineDumper<Airline> {

    private final OutputStream writer;

    /**
     * Constructor for XmlDumper.
     *
     * @param writer object pointing to destination of data.
     */
    public XmlDumper(OutputStream writer) {
        this.writer = writer;
    }


    /**
     * This function dumps the data to xml file
     *
     * @param airline object with information that needs to be dumped.
     */
    @Override
    public void dump(Airline airline) throws IOException {
        try {
            // This is an API
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            //Create a document
            DocumentBuilder documentBuilder = null;
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();


            // Create a root element for in memory tree
            Element rootElement = document.createElement("airline");
            document.appendChild(rootElement);

            // Create airline name tag
            Element airlineElement = document.createElement("name");
            // Create a text node by getting the airline name
            airlineElement.appendChild(document.createTextNode(airline.getName()));
            rootElement.appendChild(airlineElement);


            Collection<Flight> flights = airline.getFlights();
            for (Flight flight : flights) {
                Element flightElement = getFlightElement(flight, document);
                rootElement.appendChild(flightElement);
            }

            // Creates a new file format and transforms it into respective given file format
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "us-ascii");
            document.setXmlStandalone(true);

            DOMImplementation domImplementation = document.getImplementation();
            DocumentType doctype = domImplementation.createDocumentType("airline", AirlineXmlHelper.PUBLIC_ID, AirlineXmlHelper.SYSTEM_ID);
            document.appendChild(doctype);
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());

            DOMSource source = new DOMSource(document);

//            StreamResult console = new StreamResult(System.out);
//            transformer.transform(source, console);

            StreamResult file = new StreamResult(this.writer);
            transformer.transform(source, file);

        } catch (Exception e) {
            throw new IOException("Not able to write XML file." + e.getMessage());
        }

    }

    /**
     * This function gets the data of flight from command line arguments
     * and creates the elements for xml file.
     *
     * @param flight   collection of flight
     * @param document object of Document
     * @return This will return the flightElement which contains information
     * of flight.
     */

    private Element getFlightElement(Flight flight, Document document) {
        Element flightElement = document.createElement("flight");

        // Get flight number
        Element flightNumber = document.createElement("number");
        // Creating a text node by getting the flight number
        flightNumber.appendChild(document.createTextNode(String.valueOf(flight.getNumber())));
        flightElement.appendChild(flightNumber);

        // Get source
        Element srcElement = document.createElement("src");
        srcElement.appendChild(document.createTextNode(flight.getSource()));
        flightElement.appendChild(srcElement);

        // Get departure date and time
        Element departureElement = document.createElement("depart");
        flightElement.appendChild(departureElement);
        Date departureDate = flight.getDeparture();
        addDateAndTime(departureDate, document, departureElement);

        // Get destination
        Element destElement = document.createElement("dest");
        destElement.appendChild(document.createTextNode(flight.getDestination()));
        flightElement.appendChild(destElement);

        // Get arrival date and time
        Element arrivalElement = document.createElement("arrive");
        flightElement.appendChild(arrivalElement);
        Date arrivalDate = flight.getArrival();
        addDateAndTime(arrivalDate, document, arrivalElement);


        return flightElement;
    }

    /**
     * This function creates attribute dictionary for date and time tag.
     *
     * @param departureDate contains departure and arrival time date
     * @param document      object of Document
     * @param parentElement Element variable
     */
    private void addDateAndTime(Date departureDate, Document document, Element parentElement) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        // Converting Date and Time into String format
        String dateAndTime = dateFormat.format(departureDate);

        // Split the given date and time into array size 2
        String providedDate = dateAndTime.split(" ")[0];
        String providedTime = dateAndTime.split(" ")[1];

        //Date
        Element dateElement = document.createElement("date");
        dateElement.setAttribute("month", providedDate.split("/")[0]);
        dateElement.setAttribute("day", providedDate.split("/")[1]);
        dateElement.setAttribute("year", providedDate.split("/")[2]);
        parentElement.appendChild(dateElement);

        // Time
        Element timeElement = document.createElement("time");
        timeElement.setAttribute("hour", providedTime.split(":")[0]);
        timeElement.setAttribute("minute", providedTime.split(":")[1]);
        parentElement.appendChild(timeElement);
    }
}
