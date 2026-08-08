package edu.pdx.cs.joy.ysaleh;

import com.google.common.annotations.VisibleForTesting;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
  static final String WORD_PARAMETER = "word";
  static final String DEFINITION_PARAMETER = "definition";
  static final String AIRLINE_PARAMETER = "airline";
  static final String FLIGHTNUMBER_PARAMETER = "flightNumber";
  static final String SRC_PARAMETER = "src";
  static final String DEST_PARAMETER = "dest";
  //static final String DEPART_PARAMETER = "depart";
    //static final String ARRIVE_PARAMETER = "arrive";

  //private final Map<String, String> dictionary = new HashMap<>();
  private final Map<String, Airline> airlines = new HashMap<>();
  //private final ArrayList<Airline> airlines = new ArrayList<>();

  /**
   * Handles an HTTP GET request from a client by writing the definition of the
   * word specified in the "word" HTTP parameter to the HTTP response.  If the
   * "word" parameter is not specified, all of the entries in the dictionary
   * are written to the HTTP response.
   * I asked Claude for help on how I would support a second URL in this kind of application,
   * which is how I got the idea for the if-statement logic and the separate helper function.
   */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airline = getParameter( AIRLINE_PARAMETER, request );
      String src = getParameter( SRC_PARAMETER, request );
      String dest = getParameter( DEST_PARAMETER, request );
      if (src != null && dest != null) {
          log("GET " + airline + " from " + src + " to " + dest);
          writeAirline(airline, response);
      }
      else if (airline != null) {
          log("GET " + airline);
          writeAirline(airline, response);

      } else {
          missingRequiredParameter(response, AIRLINE_PARAMETER);
      }
  }



  /**
   * Handles an HTTP POST request by storing the dictionary entry for the
   * "word" and "definition" request parameters.  It writes the dictionary
   * entry to the HTTP response.
   */
  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/plain" );

      String airline = getParameter(AIRLINE_PARAMETER, request );
      if (airline == null) {
          missingRequiredParameter(response, AIRLINE_PARAMETER);
          return;
      }

      String flightNumber = getParameter(FLIGHTNUMBER_PARAMETER, request );
      if ( flightNumber == null ) {
          missingRequiredParameter( response, FLIGHTNUMBER_PARAMETER );
          return;
      }

      log("POST " + airline + " -> " + flightNumber);

      Airline retrievedAirline = this.airlines.get(airline);
      if (retrievedAirline == null) {
          retrievedAirline = new Airline(airline);
          this.airlines.put(airline, retrievedAirline);
      }

      Flight newFlight = new Flight(Integer.parseInt(flightNumber));
      retrievedAirline.addFlight(newFlight);

      PrintWriter pw = response.getWriter();
      pw.println(Messages.addedFlight(airline, newFlight.getNumber()));
      pw.flush();

      response.setStatus( HttpServletResponse.SC_OK);
  }



  /**
   * Handles an HTTP DELETE request by removing all dictionary entries.  This
   * behavior is exposed for testing purposes only.  It's probably not
   * something that you'd want a real application to expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("text/plain");

      log("DELETE all dictionary entries");

      this.airlines.clear();

      PrintWriter pw = response.getWriter();
      pw.println(Messages.allAirlinesDeleted());
      pw.flush();

      response.setStatus(HttpServletResponse.SC_OK);

  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter( HttpServletResponse response, String parameterName )
      throws IOException
  {
      String message = Messages.missingRequiredParameter(parameterName);
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }

  /**
   * Writes the flights of the given airline to the HTTP response.
   *
   * The text of the message is formatted with {@link TextDumper}
   */
  private void writeAirline(String airlineName, HttpServletResponse response) throws IOException {

      Airline retrievedAirline = this.airlines.get(airlineName);
      if (retrievedAirline == null) {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);

    } else {
      PrintWriter pw = response.getWriter();

      TextDumper dumper = new TextDumper(pw);
      dumper.dump(retrievedAirline, null, null);

      response.setStatus(HttpServletResponse.SC_OK);
    }
  }


    private void writeAirlineSrcDest(String airlineName, String src, String dest, HttpServletResponse response) throws IOException {

        Airline retrievedAirline = this.airlines.get(airlineName);
        if (retrievedAirline == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();

            TextDumper dumper = new TextDumper(pw);
            dumper.dump(retrievedAirline, src, dest);

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }



  /**
   * Returns the value of the HTTP request parameter with the given name.
   *
   * @return <code>null</code> if the value of the parameter is
   *         <code>null</code> or is the empty string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }

  @VisibleForTesting
  Airline getAirline(String airlineName) {
      return this.airlines.get(airlineName);
  }

  @Override
  public void log(String msg) {
    System.out.println(msg);
  }


}
