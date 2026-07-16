package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.AbstractFlight;

/**
 * Flight class that represents Flight objects. Contains a source airport, a destination airport,
 * a departure time, an arrival time, and an identification number.
 */
public class Flight extends AbstractFlight {
  private String source;  //  Three-letter code of departure airport
  private String destination;  //  Three-letter code of departure airport
  private String departureTime;
  private String arrivalTime;
  private int id;

  /**
   * Default constructor. Just here to prevent errors.
   */
  public Flight() {}

  /**
   * Argument constructor. Initializes fields for a new Flight object with specified parameters.
   * @param flightNum the indentification number for the flight
   * @param src the three-letter code of the source airport
   * @param depart the date and time of flight departure
   * @param dest the three-letter code of the departure airport
   * @param arrive the date and time of flight departure
   */
  public Flight(int flightNum, String src, String depart, String dest, String arrive) {
    id = flightNum;
    source = src;
    departureTime = depart;
    destination = dest;
    arrivalTime = arrive;
  }

  /**
   * Getter function for Flight identification number
   * @return the Flight object's ID number
   */
  @Override
  public int getNumber() {
    return id;
  }

  /**
   * Getter function for Flight source airport code
   * @return the Flight object's source airport code
   */
  @Override
  public String getSource() { return source; }

  /**
   * Getter function for Flight departure date and time
   * @return the Flight object's date and time of departure
   */
  @Override
  public String getDepartureString() { return departureTime; }

  /**
   * Getter function for Flight destination airport code
   * @return the Flight object's destination airport code
   */
  @Override
  public String getDestination() { return destination; }

  /**
   * Getter function for Flight arrival date and time
   * @return the Flight object's date and time of arrival
   */
  @Override
  public String getArrivalString() { return arrivalTime; }

}
