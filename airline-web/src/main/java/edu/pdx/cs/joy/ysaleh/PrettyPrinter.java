package edu.pdx.cs.joy.ysaleh;

import com.google.common.annotations.VisibleForTesting;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;

public class PrettyPrinter {
  private final Writer writer;

  @VisibleForTesting
  static String formatFlightCount(int count )
  {
    return String.format( "Airline on server contains %d flights", count );
  }

  @VisibleForTesting
  static String formatFlightEntry(String airlineName, String flight )
  {
    return String.format("  %s -> %s", airlineName, flight);
  }


  public PrettyPrinter(Writer writer) {
    this.writer = writer;
  }

  public void dump(Airline airlineToPrint, String src, String dest) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ) {

      ArrayList<Flight> flights = airlineToPrint.getFlights();

      if (src != null && dest != null) {
        pw.println("Flights in Airline " + airlineToPrint.getName() + " that depart from " + src + " and arrive at " + dest + ":");
        for (Flight f : flights) {
          if (f.getSource().equals(src.toUpperCase()) && f.getDestination().equals(dest.toUpperCase())) {
            pw.println("\tFlight #" + f.getNumber() + " departs " + f.getSource() + " at " + f.getDepartureString() + " and arrives at " + f.getDestination() + " at " + f.getArrivalString());
          }
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
