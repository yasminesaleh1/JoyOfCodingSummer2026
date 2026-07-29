package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.AbstractFlight;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.time.*;
import java.text.DateFormat;

/**
 * Flight class that represents Flight objects. Contains a source airport, a destination airport,
 * a departure time, an arrival time, and an identification number.
 */
public class Flight extends AbstractFlight implements Comparable<Flight> {
  private String source;  //  Three-letter code of departure airport
  private String destination;  //  Three-letter code of departure airport
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private int id;


  /**
   * Default constructor. Just here to prevent errors.
   */
  public Flight() {}

  /**
   * Argument constructor. Initializes fields for a new Flight object with specified parameters.
   * Inspiration was taken from the following code snippet provided by Google AI overview
   * after I searched "java localdatetime parse from string":
   * ```
   * import java.time.LocalDateTime;
   * import java.time.format.DateTimeFormatter;
   *
   * public class Main {
   *     public static void main(String[] args) {
   *         String customString = "29/07/2026 14:30:15";
   *
   *         // Define the pattern matching your text
   *         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
   *
   *         // Parse with the formatter
   *         LocalDateTime dateTime = LocalDateTime.parse(customString, formatter);
   *
   *         System.out.println(dateTime); // Output: 2026-07-29T14:30:15
   *     }
   * }
   * ```
   * @param flightNum the identification number for the flight
   * @param src the three-letter code of the source airport
   * @param depart the date and time of flight departure
   * @param dest the three-letter code of the departure airport
   * @param arrive the date and time of flight departure
   */
  public Flight(int flightNum, String src, String depart, String dest, String arrive) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:m a"); //  eg: 01/02/2026 9:16 PM
    departureTime = LocalDateTime.parse(depart, formatter);
    arrivalTime = LocalDateTime.parse(arrive, formatter);
    id = flightNum;
    source = src;
    destination = dest;
  }

  /**
   * compareTo method that defines how Flight objects will be compared to each other,
   * to help with sorting by either source airport code or departure time.
   * @param f2 the Flight object to be compared.
   * @return the integer comparison offset
   */
  public int compareTo(Flight f2) {
    // if the two flights depart from the same airport, sort by departure time
    if (source.compareTo(f2.source) == 0) { return departureTime.compareTo(f2.departureTime); }

    // otherwise, default to sorting by source airport code
    else { return source.compareTo(f2.source); }
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
  public String getDepartureString() {
    int shortFormat = DateFormat.SHORT;
    DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(shortFormat, shortFormat);
    return dateTimeFormat.format(departureTime); }

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
  public String getArrivalString() { int shortFormat = DateFormat.SHORT;
    DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(shortFormat, shortFormat);
    return dateTimeFormat.format(arrivalTime); }

  /**
   * getString() method which calls super's already-implemented toString() method
   * @return the string representing the Flight object
   */
  public String getString() { return super.toString(); }

  @Override
  public LocalDateTime getArrival() { return arrivalTime; }

  @Override
  public LocalDateTime getDeparture() { return departureTime; }

}
