package edu.pdx.cs.joy.ysaleh;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs.joy.ParserException;
import edu.pdx.cs.joy.web.HttpRequestHelper;
import edu.pdx.cs.joy.web.HttpRequestHelper.Response;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs.joy.web.HttpRequestHelper.*;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.
 */
public class AirlineRestClient
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final HttpRequestHelper http;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port number
     */
    public AirlineRestClient( String hostName, int port )
    {
        this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
    }

    @VisibleForTesting
    AirlineRestClient(HttpRequestHelper http) {
      this.http = http;
    }



  /**
   * Returns the Airline object with the given name
   * @param airline the name of the airline to search for
   */
  public Airline getAirline(String airline) throws IOException, ParserException {
    Response response = http.get(Map.of(AirlineServlet.AIRLINE_PARAMETER, airline));
    throwExceptionIfNotOkayHttpStatus(response);
    String content = response.getContent();

    TextParser parser = new TextParser(new StringReader(content));
    return parser.parse();  //.get(airline);
  }

    /**
     * Posts a new flight to the server
     * @param airline the airline to add the flight to
     * @param flightNum the number of the new flight
     * @param src the source airport of the new flight
     * @param depart the departure time of the new flight
     * @param dest the destination airport of the new flight
     * @param arrive the arrival time of the new flight
     * @throws IOException thrown by http.post()
     */
  public void addFlight(String airline, int flightNum, String src, String depart, String dest, String arrive) throws IOException {
    Response response = http.post(Map.of(AirlineServlet.AIRLINE_PARAMETER, airline,
            AirlineServlet.FLIGHTNUMBER_PARAMETER, String.valueOf(flightNum),
            AirlineServlet.SRC_PARAMETER, String.valueOf(src),
            AirlineServlet.DEPART_PARAMETER, String.valueOf(depart),
            AirlineServlet.DEST_PARAMETER, String.valueOf(dest),
            AirlineServlet.ARRIVE_PARAMETER, String.valueOf(arrive)));
    throwExceptionIfNotOkayHttpStatus(response);
  }


    /**
     * Throws an exception if an HTTP status other than OK (200) is received
     * @param response the HTTP response
     */
  private void throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getHttpStatusCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
  }

}
