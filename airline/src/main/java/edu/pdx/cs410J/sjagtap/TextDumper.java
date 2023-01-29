package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
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

public class TextDumper implements AirlineDumper<Airline> {
    private final Writer writer;

    // create a new file
    // path?
    public TextDumper(Writer writer) { // did not understand.
        this.writer = writer;
    }

    @Override
    public void dump(Airline airline) {
        try (
                PrintWriter pw = new PrintWriter(this.writer)
        ) {
            Collection<Flight> flight1 = airline.getFlights();
            Iterator<Flight> iterator = flight1.iterator();
            pw.println(airline.getName());
            while (iterator.hasNext()) {
                Flight details = iterator.next();
                int flightNumber = details.getNumber();
                pw.println(flightNumber);
                String src = details.getSource();
                pw.println(src);
                String depart = details.getDepartureString();
                pw.println(depart);
                String dst = details.getDestination();
                pw.println(dst);
                String arrive = details.getArrivalString();
                pw.println(arrive);
            }
            pw.flush();
        }
    }
}
