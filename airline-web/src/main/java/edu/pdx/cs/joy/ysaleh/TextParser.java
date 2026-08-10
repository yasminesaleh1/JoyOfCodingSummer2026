package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

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

      /*for (String line = br.readLine(); line != null; line = br.readLine()) {
        if (airline == null) {
          airline = new Airline(line);
        }
        else {
          airline.addFlight(new Flight(Integer.parseInt(line)), );
        }
      }*/

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
