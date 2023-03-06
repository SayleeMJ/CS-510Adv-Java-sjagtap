package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class PrettyPrinter {
  private final Writer writer;

  @VisibleForTesting
  static String formatWordCount(int count )
  {
    return String.format( "Dictionary on server contains %d words", count );
  }

  @VisibleForTesting
  static String formatDictionaryEntry(String word, String definition )
  {
    return String.format("  %s -> %s", word, definition);
  }


  public PrettyPrinter(Writer writer) {
    this.writer = writer;
  }

  public void dump(Airline airline) {
    PrintWriter printWriter = new PrintWriter(this.writer);
    String airlineName = airline.getName();
    printWriter.println("Flights for airline " + airlineName);
    printWriter.println("---------------------------------------------");

    Collection<Flight> flights = airline.getFlights();
    Iterator<Flight> iterator = flights.iterator();
    int i = 1;
    while (iterator.hasNext()){
      Flight flight = iterator.next();
      String flightDetails = flight.ToStringPretty();
      printWriter.println(i+". " + flightDetails +"\n");
      i++;
    }
    printWriter.flush();
  }
}
