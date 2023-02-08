package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

/**
 * A skeletal implementation of the <code>TextDumper</code> class for Project 2.
 */

/**
 * Writer class represents a stream of characters
 * implements appendable, closeable, flushable
 * PrintWriter class writes any type of data e.g. int, float, double, string
 * or object in the form of text either on the console or in a file in java
 */

public class PrettyPrinter implements AirlineDumper<Airline> {
    private final Writer writer;

    /**
     * Constructor for print pretty class.
     * @param writer object pointing to destination for writes.
     */
    public PrettyPrinter(Writer writer) { // did not understand.
        this.writer = writer;
    }

    /**
     * Dump data to destination pointed by writer.
     * @param airline object containing information to dump.
     */
    @Override
    public void dump(Airline airline) {
        PrintWriter pw = new PrintWriter(this.writer);

        String airlineName = airline.getName();
        pw.println("AirlineName " + airlineName);

        Collection<Flight> flight1 = airline.getFlights();
        Iterator<Flight> iterator = flight1.iterator();
        while (iterator.hasNext()) {
            Flight flightObject = iterator.next();
            String flightDetails = flightObject.ToStringPretty();
            pw.println(flightDetails);
        }
        pw.flush();
    }
}
