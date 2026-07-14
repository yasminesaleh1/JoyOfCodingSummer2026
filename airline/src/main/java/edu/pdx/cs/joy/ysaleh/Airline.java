package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
  private final String name;
  private ArrayList<Flight> airlineFlights = new ArrayList<>();

  public Airline(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  // main calls this and puts the returned flight into a variable...
  public Flight createFlight(int flightNum, String src, String depart, String dest, String arrive) {
    //if (flightNum < 0 || )

    return new Flight(flightNum, src, depart, dest, arrive);
  }

  // then main calls this with the variable returned from createFlight()
  @Override
  public void addFlight(Flight flight) {
    airlineFlights.add(flight);
  }

  @Override
  public Collection<Flight> getFlights() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }
}
