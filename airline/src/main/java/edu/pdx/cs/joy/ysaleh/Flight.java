package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.AbstractFlight;

public class Flight extends AbstractFlight {
  private String source;  //  Three-letter code of departure airport
  private String destination;  //  Three-letter code of departure airport
  private String departureTime;
  private String arrivalTime;
  private int id;

  public Flight() {}

  // argument constructor
  public Flight(int flightNum, String src, String depart, String dest, String arrive) {
    id = flightNum;
    source = src;
    departureTime = depart;
    destination = dest;
    arrivalTime = arrive;
  }

  @Override
  public int getNumber() {
    return id;
  }

  @Override
  public String getSource() { return source; }

  @Override
  public String getDepartureString() { return null; }  // temporary for project 1

  @Override
  public String getDestination() { return destination; }

  @Override
  public String getArrivalString() { return null; }   // temporary for project 1

}
