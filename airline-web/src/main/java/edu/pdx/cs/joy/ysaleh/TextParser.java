package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A class to parse an airline's flights.
 */
public class TextParser {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  /**
   * Parses an airline's flights by reading them into an airline object.
   * @return the newly-parsed airline.
   * @throws ParserException if the information being parsed is mal-formatted then this will be thrown
   */
  public Airline parse() throws ParserException {
    Airline airline = null;
    String num;
    String airlineName;
    int flightNum;
    String src;
    String depart;
    String dest;
    String arrive;

    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {
      airlineName = br.readLine();
      airline = new Airline(airlineName);
      while ((num = br.readLine()) != null) {
        flightNum = Integer.parseInt(num);
        src = br.readLine();
        depart = br.readLine();
        dest = br.readLine();
        arrive = br.readLine();
        Flight newFlight = new Flight(flightNum, src, depart, dest, arrive);
        airline.addFlight(newFlight);
      }

    } catch (IOException e) {
      throw new ParserException("While parsing Airline", e);
    }

    return airline;
  }
}
