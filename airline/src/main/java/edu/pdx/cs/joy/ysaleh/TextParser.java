package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.AirlineParser;
import edu.pdx.cs.joy.ParserException;

import java.io.*;

/**
 * A skeletal implementation of the <code>TextParser</code> class for Project 2.
 */
public class TextParser implements AirlineParser<Airline> {
  private final File file;

  public TextParser(String fileName) {
    file = new File(fileName);
  }  // constructor

  @Override
  public Airline parse() throws ParserException {
    try {
      if (!file.exists()) { file.createNewFile(); }

      // got this style of file I/O from the koans in intermediate/AboutFileIO.java
      FileReader fr = new FileReader(file);

      BufferedReader br = new BufferedReader(fr);

      Flight newFlight;
      String flightNumberStr;
      int flightNumber;
      String src;
      String depart;
      String dest;
      String arrive;

      String airlineName = br.readLine();
      if (airlineName == null) { return null; }   // empty file; nothing to read
      Airline newAirline = new Airline(airlineName);


      while ((flightNumberStr = br.readLine()) != null) {
        // read in flight attributes
        flightNumber = Integer.parseInt(flightNumberStr);
        src = br.readLine();
        depart = br.readLine();
        dest = br.readLine();
        arrive = br.readLine();

        // create new flight with above attributes
        newFlight = newAirline.createFlight(flightNumber, src, depart, dest, arrive);

        // add new flight to airline
        newAirline.addFlight(newFlight);

      }

      br.close();

      return newAirline;

    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }
}
