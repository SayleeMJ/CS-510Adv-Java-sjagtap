package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

  private final String flightNumber;
  private final String src;
  private final String dst;
  private final String depart;
  private final String arrive;


  public Flight(String flightNumber, String src, String depart, String dst, String arrive) {
    this.flightNumber = flightNumber;
    this.src = src;
    this.depart = depart;
    this.dst = dst;
    this.arrive = arrive;
  }

  public Flight(){
    this.flightNumber ="12";
    this.src = "src";
    this.dst = "dst";
    this.depart = "1/1/2000";
    this.arrive = "00:00";

  }

  @Override
  public int getNumber() {
    return 42;
  }

  @Override
  public String getSource() {
    return this.src;
  }

  @Override
  public String getDepartureString() {
    return this.depart;
  }

  @Override
  public String getDestination() {
    return this.dst;
  }

  @Override
  public String getArrivalString() {
    return this.arrive;
  }
}
