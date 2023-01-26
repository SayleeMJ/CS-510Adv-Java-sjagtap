package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Airline extends AbstractAirline<Flight> {
  private final String name;
  ArrayList<String> arrayList = new ArrayList<>();
  public Airline(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addFlight(Flight flight) {
    arrayList.add(getName());
    arrayList.add(String.valueOf(flight.getNumber()));
    System.out.println("Size of arraylist: "+arrayList.size());
//    arrayList.add(flight.getSource());
//    arrayList.add(flight.getDestination());
//    arrayList.add(flight.getArrivalString());
//    arrayList.add(flight.getDepartureString());
   // throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public Collection<Flight> getFlights() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }
}
