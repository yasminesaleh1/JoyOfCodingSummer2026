package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.AirlineDumper;

import java.io.*;

/**
 * TextDumper class which contains the method to write airline information to a file
 */
public class TextDumper implements AirlineDumper<Airline> {
  private final File file;

  /**
   * Argument constructor. Initializes fields for a new TextDumper object with specified parameters.
   * @param fileName the name of the file to parse
   */
  public TextDumper(String fileName) {  // constructor
    file = new File(fileName);
  }

  /**
   * dump() method which takes the file name defined in the constructor and creates it (if needed),
   * and otherwise writes the airline's content into the file.
   * @param airline The airline whose information will be written to the file
   */
  @Override
  public void dump(Airline airline) {
    try {
      if (!file.exists()) { file.createNewFile(); }

      // got this style of file I/O from the koans in intermediate/AboutFileIO.java
      FileWriter fw = new FileWriter(file);
      PrintWriter pw = new PrintWriter(fw);

      pw.println(airline.getName());

      // print flight info line-by-line, for each flight in the airline
      for (Flight airlineFlight : airline.getFlights()) {
        pw.println(airlineFlight.getNumber());
        pw.println(airlineFlight.getSource());
        pw.println(airlineFlight.getOriginalDepartureTime());
        pw.println(airlineFlight.getDestination());
        pw.println(airlineFlight.getOriginalArrivalTime());
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
