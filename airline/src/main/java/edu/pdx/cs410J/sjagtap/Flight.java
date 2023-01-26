package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AbstractFlight;

import java.util.ArrayList;

public class Flight extends AbstractFlight {

  private final int number;
  private final String src;
  private final String dest ;
  private final String date;
  private final String time;

  public Flight(int number, String src, String dest, String date, String time) {
    this.number = number;
    this.src = src;
    this.dest = dest;
    this.date = date;
    this.time = time;
  }

  public Flight(){
    this.number =12;
    this.src = "src";
    this.dest = "dst";
    this.date = "1/1/2000";
    this.time = "00:00";
  }

  @Override
  public int getNumber() {
    return 42;
  }

  @Override
  public String getSource() {

    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDepartureString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDestination() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getArrivalString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }
}
