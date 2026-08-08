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


  public PrettyPrinter(Writer writer) {
    this.writer = writer;
  }

  public void dump(Airline airlineToPrint) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ) {

      ArrayList<Flight> flights = airlineToPrint.getFlights();

      pw.println("\nAirline " + airlineToPrint.getName() + " contains " + flights.size() + " flights:");
      for (Flight f : flights) {
        pw.println("\tFlight #" + f.getNumber());
      }

      pw.flush();
    }

  }
}
