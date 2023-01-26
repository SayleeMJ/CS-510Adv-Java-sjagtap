package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Airline extends AbstractAirline<Flight> {
  private final String name;
  ArrayList<Flight> arrayList;

  public Airline(String name) {
    this.name = name;
    arrayList = new ArrayList<>();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addFlight(Flight flight) {
    arrayList.add(flight);
  }

  @Override
  public Collection<Flight> getFlights() {
    return arrayList;
  }
}
