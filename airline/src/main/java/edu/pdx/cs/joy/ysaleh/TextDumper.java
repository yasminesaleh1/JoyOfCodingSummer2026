package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.AirlineDumper;

import java.io.*;

/**
 * A skeletal implementation of the <code>TextDumper</code> class for Project 2.
 */
public class TextDumper implements AirlineDumper<Airline> {
  private final File file;

  public TextDumper(String fileName) {
    file = new File(fileName);
  }

  @Override
  public void dump(Airline airline) {
    try {
      // got this style of file I/O from the koans in intermediate/AboutFileIO.java
      FileWriter fw = new FileWriter(file);
      PrintWriter pw = new PrintWriter(fw);
      pw.println(airline.getName());

      // print flight info line-by-line, for each flight in the airline
      for (Flight airlineFlight : airline.getFlights()) {
        pw.println(airlineFlight.getNumber());
        pw.println(airlineFlight.getSource());
        pw.println(airlineFlight.getDepartureString());
        pw.println(airlineFlight.getDestination());
        pw.println(airlineFlight.getArrivalString());
      }
      pw.flush();
      pw.close();

    }
    catch (IOException e) {
      System.out.println("Error when trying to open file. The file cannot be opened or created. " +
              "Check to make sure you entered a valid file name (not a directory name) and try again.");
      return;
    }
  }
}
