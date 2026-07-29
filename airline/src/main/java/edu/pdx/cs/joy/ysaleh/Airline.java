package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * Airline class to represent Airline objects. Contains a name for the airline and an Array List of flights.
 */
public class Airline extends AbstractAirline<Flight> {
  private final String name;
  private ArrayList<Flight> airlineFlights = new ArrayList<>();

  /**
   * Argument constructor. Initializes fields for a new Airline object with specified parameters.
   * @param name the name of the new airline
   */
  public Airline(String name) {
    this.name = name;
  }

  /**
   * Getter function for the Airline name
   * @return the Airline's name
   */
  @Override
  public String getName() {
    return this.name;
  }


  /**
   * createFlight function. Creates a new Flight object with specified fields for the flight.
   * Main calls this and puts the returned flight into a local variable.
   * @param flightNum the indentification number for the flight
   * @param src the three-letter code of the source airport
   * @param depart the date and time of flight departure
   * @param dest the three-letter code of the departure airport
   * @param arrive the date and time of flight departure
   * @return the newly-created flight object
   */
  public Flight createFlight(int flightNum, String src, String depart, String dest, String arrive) {
    return new Flight(flightNum, src, depart, dest, arrive);
  }


  /**
   * addFlight function. Adds a flight to the Airline object's airlineFlights ArrayList, then sort all the flights in the array list.
   * Main calls this with the variable returned from createFlight().
   * @param flight the flight to-be-added to the Airline object's ArrayList
   */
  @Override
  public void addFlight(Flight flight) {
    airlineFlights.add(flight);
    // sorts the list according to what I defined in the overloaded compareTo() method
    airlineFlights.sort(null);
  }

  @Override
  public Collection<Flight> getFlights() {
    //throw new UnsupportedOperationException("This method is not implemented yet");
    return airlineFlights;
  }
}
