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
 * This servlet provides a REST API for working with an <code>Airline</code>.
 */
public class AirlineServlet extends HttpServlet {
  static final String WORD_PARAMETER = "word";
  static final String DEFINITION_PARAMETER = "definition";
  static final String AIRLINE_PARAMETER = "airline";
  static final String FLIGHTNUMBER_PARAMETER = "flightNumber";
  static final String SRC_PARAMETER = "src";
  static final String DEST_PARAMETER = "dest";
  static final String DEPART_PARAMETER = "depart";
  static final String ARRIVE_PARAMETER = "arrive";

  private final Map<String, Airline> airlines = new HashMap<>();

  /**
   * Handles an HTTP GET request from a client by writing the name of the
   * airline specified in the "AIRLINE" HTTP parameter to the HTTP response,
   * and optionally writes the SRC and DEST airports to search for flights between them.
   * I asked Claude for help on how I would support a second URL in this kind of application,
   * which is how I got the idea for the if-statement logic and the separate helper function.
   * @param request the HTTP request
   * @param response the HTTP response
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
          writeAirlineSrcDest(airline, src, dest, response);
      }
      else if (airline != null) {
          log("GET " + airline);
          writeAirline(airline, response);

      } else {
          missingRequiredParameter(response, AIRLINE_PARAMETER);
      }
  }



  /**
   * Handles an HTTP POST request by storing the airline's flight entry for the
   * "AIRLINE", "FLIGHTNUMBER", "SRC", "DEPART", "DEST", and "ARRIVE" request parameters.
   * It writes the flight entry to the HTTP response.
   * @param request the HTTP request
   * @param response the HTTP response
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

      String src = getParameter(SRC_PARAMETER, request );
      if ( src == null ) {
          missingRequiredParameter( response, SRC_PARAMETER );
          return;
      }

      String depart = getParameter(DEPART_PARAMETER, request );
      if ( depart == null ) {
          missingRequiredParameter( response, DEPART_PARAMETER );
          return;
      }

      String dest = getParameter(DEST_PARAMETER, request );
      if ( dest == null ) {
          missingRequiredParameter( response, DEST_PARAMETER );
          return;
      }

      String arrive = getParameter(ARRIVE_PARAMETER, request );
      if ( arrive == null ) {
          missingRequiredParameter( response, ARRIVE_PARAMETER );
          return;
      }


      log("POST " + airline + " -> " + flightNumber + " " + src + " " + depart + " " + dest + " " + arrive);

      Airline retrievedAirline = this.airlines.get(airline);
      if (retrievedAirline == null) {
          retrievedAirline = new Airline(airline);
          this.airlines.put(airline, retrievedAirline);
      }

      Flight newFlight = new Flight(Integer.parseInt(flightNumber), src, depart, dest, arrive);
      retrievedAirline.addFlight(newFlight);

      PrintWriter pw = response.getWriter();
      pw.println(Messages.addedFlight(airline, flightNumber, src, depart, dest, arrive));
      pw.flush();

      response.setStatus( HttpServletResponse.SC_OK);
  }



  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   * @param response the HTTP response
   * @param parameterName the missing parameter
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
   * @param airlineName the name of the airline to write
   * @param response the http response
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

    /**
     * Writes the flights of the given airline from a specific source airport
     * and a specific destination airport to the HTTP response.
     *
     * The text of the message is formatted with {@link TextDumper}
     * @param airlineName the name of the airline to write
     * @param src the source airport to search for
     * @param dest the destination airport to search for
     * @param response the http response
     */
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
   * @param name the name of the parameter to get
   * @param request the HTTP request
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

    /**
     * getter for an airline
     * @param airlineName the airline to retrieve
     * @return The retrieved airline
     */
  @VisibleForTesting
  Airline getAirline(String airlineName) {
      return this.airlines.get(airlineName);
  }

    /**
     * Logs a message
     * @param msg a <code>String</code> specifying the message to be written to the log file
     */
  @Override
  public void log(String msg) {
    System.out.println(msg);
  }


}
