package edu.pdx.cs.joy.ysaleh;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;

/**
 * A class to dump flights from a specific airline
 */
public class TextDumper {
  private final Writer writer;

  /**
   * initializes the writer
   * @param writer the writer to initialize
   */
  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * A dumper function to dump an airline's flights and print them. Either prints all flights in an airline
   * or prints all flights from a specific source airport to a specific destination airport.
   * @param airline the airline whose flights will be printed.
   * @param src the source airport to search for flights for
   * @param dest the destination airport to search for flights for
   */
  public void dump(Airline airline, String src, String dest) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ){
      if (src != null && dest != null) {
        pw.println(airline.getName());
        for (Flight f : airline.getFlights()) {
          if (f.getSource().equals(src.toUpperCase()) && f.getDestination().equals(dest.toUpperCase())) {
            pw.println(f.getNumber());
            pw.println(f.getSource());
            pw.println(f.getDepartureString());
            pw.println(f.getDestination());
            pw.println(f.getArrivalString());
          }
        }
      }
      else {
        pw.println(airline.getName());
        for (Flight f : airline.getFlights()) {
          pw.println(f.getNumber());
          pw.println(f.getSource());
          pw.println(f.getDepartureString());
          pw.println(f.getDestination());
          pw.println(f.getArrivalString());
        }
      }

      pw.flush();
    }
  }
}
