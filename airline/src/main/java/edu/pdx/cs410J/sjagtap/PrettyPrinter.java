package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AirlineDumper;

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

    public PrettyPrinter(Writer writer) { // did not understand.
        this.writer = writer;
    }

    @Override
    public void dump(Airline airline) {
        PrintWriter pw = new PrintWriter(this.writer);
        Collection<Flight> flightList = airline.getFlights();
        Iterator<Flight> iterator = flightList.iterator();
        while (iterator.hasNext()) {
            Flight flight = iterator.next();
            String flightTOString = flight.toString();
            pw.println(flightTOString);
        }
        pw.flush();
    }
}
