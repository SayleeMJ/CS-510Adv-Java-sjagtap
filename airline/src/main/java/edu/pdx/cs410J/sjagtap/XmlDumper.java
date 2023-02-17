package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlDumper implements AirlineDumper<Airline> {
    private final OutputStream writter;

    public XmlDumper(OutputStream writter) {
        this.writter = writter;
    }

    @Override
    public void dump(Airline airline) throws IOException {
        try {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = null;
            dbBuilder = factory.newDocumentBuilder();
            Document doc = dbBuilder.newDocument();

            // Create in-memory tree.
            Element rootElement = doc.createElement("airline");
            doc.appendChild(rootElement);

            // Add airline name tag
            Element airlineElement = doc.createElement("name");
            airlineElement.appendChild(doc.createTextNode(airline.getName()));
            rootElement.appendChild(airlineElement);

            Collection<Flight>  flightList = airline.getFlights();
            for (Flight flight: flightList) { // New syntax in java for array list.
                Element flightElement = GetFlightElement(flight, doc);
                rootElement.appendChild(flightElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Code to add <?xml version="1.0" encoding="us-ascii"?>
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "us-ascii");
            doc.setXmlStandalone(true); // remove standalone from xml tag <?xml version="1.0" encoding="us-ascii" standalone="no"?>

            // Code to add <!DOCTYPE airline SYSTEM "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd">
            DOMImplementation domImpl = doc.getImplementation();
            DocumentType doctype = domImpl.createDocumentType("airline", AirlineXmlHelper.PUBLIC_ID, AirlineXmlHelper.SYSTEM_ID);
            doc.appendChild(doctype);
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());

            // Write to file
            DOMSource source = new DOMSource(doc);

            // Write to console.
            StreamResult console = new StreamResult(System.out);
            transformer.transform(source, console);

            // Write to file.
            StreamResult file = new StreamResult(this.writter);
            transformer.transform(source, file);

        } catch (Exception e) {
            throw new IOException("Unable to write XML file." + e.getMessage());
        }
    }

    private Element GetFlightElement(Flight flight, Document doc) {
        Element flightElement = doc.createElement("flight");

        // Get Number
        Element numberElement = doc.createElement("number");
        numberElement.appendChild(doc.createTextNode(String.valueOf(flight.getNumber())));
        flightElement.appendChild(numberElement);

        // Get src
        Element srcElement = doc.createElement("src");
        srcElement.appendChild(doc.createTextNode(String.valueOf(flight.getSource())));
        flightElement.appendChild(srcElement);

        // get departure date and time
        Element departurElement = doc.createElement("depart");
        flightElement.appendChild(departurElement);
        Date departureDate = flight.getDeparture();
        AddDate(departureDate, doc, departurElement);

        // get dest
        Element destElement = doc.createElement("dest");
        destElement.appendChild(doc.createTextNode(String.valueOf(flight.getDestination())));
        flightElement.appendChild(destElement);

        // arrival date and time.
        Element arrivateDateTimeElement = doc.createElement("arrive");
        flightElement.appendChild(arrivateDateTimeElement);
        Date arrivalDateTime = flight.getArrival();
        AddDate(arrivalDateTime, doc, arrivateDateTimeElement);

        return flightElement;
    }

    private static void AddDate(Date date, Document doc, Element parentElement) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        String departDateTime = dateFormat.format(date);
        String DepartureDate = departDateTime.split(" ")[0];
        String DepartureTime = departDateTime.split(" ")[1];

        // Date
        Element dateElement = doc.createElement("date");
        dateElement.setAttribute("month", DepartureDate.split("/")[0]);
        dateElement.setAttribute("day", DepartureDate.split("/")[1]);
        dateElement.setAttribute("year", DepartureDate.split("/")[2]);
        parentElement.appendChild(dateElement);

        // Time
        Element timeElement = doc.createElement("time");
        timeElement.setAttribute("hour", DepartureTime.split(":")[0]);
        timeElement.setAttribute("minute", DepartureTime.split(":")[1]);
        parentElement.appendChild(timeElement);
    }
}
