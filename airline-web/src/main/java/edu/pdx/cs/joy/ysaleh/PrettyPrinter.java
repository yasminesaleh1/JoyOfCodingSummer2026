package edu.pdx.cs.joy.ysaleh;

import com.google.common.annotations.VisibleForTesting;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;

/**
 * A class to pretty-print airline flights to the user
 */
public class PrettyPrinter {
  private final Writer writer;

  /**
   * initializes the writer
   * @param writer the writer to intialize
   */
  public PrettyPrinter(Writer writer) {
    this.writer = writer;
  }

  /**
   * Pretty prints the flight(s) to the user. Either prints all flights in an airline
   * or prints all flights from a specific source airport to a specific destination airport.
   * @param airlineToPrint the airline whose flights will be printed
   * @param src the source airport to search for flights for
   * @param dest the destination airport to search for flights for
   */
  public void dump(Airline airlineToPrint, String src, String dest) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ) {

      ArrayList<Flight> flights = airlineToPrint.getFlights();

      if (src != null && dest != null) {
        int count = 0;
        pw.println("Flights in Airline " + airlineToPrint.getName() + " that depart from " + src + " and arrive at " + dest + ":");
        for (Flight f : flights) {
          if (f.getSource().equals(src.toUpperCase()) && f.getDestination().equals(dest.toUpperCase())) {
            pw.println("\tFlight #" + f.getNumber() + " departs " + f.getSource() + " at " + f.getDepartureString() + " and arrives at " + f.getDestination() + " at " + f.getArrivalString());
            ++count;
          }
        }
        if (count == 0) {
          pw.println("No flights from " + src + " to " + dest + " were found in " + airlineToPrint.getName());
        }
      }
      else {
        pw.println("\nAirline " + airlineToPrint.getName() + " contains " + flights.size() + " flights:");
        for (Flight f : flights) {
          pw.println("\tFlight #" + f.getNumber() + " departs " + f.getSource() + " at " + f.getDepartureString() + " and arrives at " + f.getDestination() + " at " + f.getArrivalString());
        }
      }


      pw.flush();
    }

  }
}
