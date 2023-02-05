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

    public PrettyPrinter(Writer writer) { // did not understand.
        this.writer = writer;
    }

    @Override
    public void dump(Airline airline) {
        PrintWriter pw = new PrintWriter(this.writer);

        Collection<Flight> flight1 = airline.getFlights();
        Iterator<Flight> iterator = flight1.iterator();
        pw.println(airline.getName());

        while (iterator.hasNext()) {
            Flight details = iterator.next();
            int flightNumber = details.getNumber();
            pw.println(flightNumber);

            String src = details.getSource();
            String source = AirportNames.getName(src);
            pw.println(source);

            String depart = details.getDepartureString();
            pw.println(depart);

            String dst = details.getDestination();
            String destination = AirportNames.getName(dst);
            pw.println(destination);

            String arrive = details.getArrivalString();
            pw.println(arrive);

            long duration = details.durationOfFlight();
            pw.println( "Duration of flight: " + duration);

        }
        pw.flush();
    }
}
