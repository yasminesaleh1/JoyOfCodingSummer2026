package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.ParserException;
import edu.pdx.cs.joy.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@TestMethodOrder(MethodName.class)
class AirlineRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AirlineRestClient newAirlineRestClient() {
    int port = Integer.parseInt(PORT);
    return new AirlineRestClient(HOSTNAME, port);
  }


  /*@Test
  void test1EmptyServerContainsNoDictionaryEntries() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();
    Map<String, String> dictionary = client.getAllDictionaryEntries();
    assertThat(dictionary.size(), equalTo(0));
  }*/

  @Test
  void test2DefineOneAirline() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();
    String testAirline = "TEST AIRLINE";
    int testFlightNum = 10;
    String testSrc = "PDX";
    String testDepart = "03/02/2026 12:57 PM";
    String testDest = "LAX";
    String testArrive = "03/02/2026 2:57 PM";
    client.addFlight(testAirline, testFlightNum, testSrc, testDepart, testDest, testArrive);

    Flight flight = client.getAirline(testAirline).getFlights().getFirst();
    assertEquals(testFlightNum, flight.getNumber());
    assertEquals(testSrc, flight.getSource());
    assertEquals(testDepart, flight.getDepartureString());
    assertEquals(testDest, flight.getDestination());
    assertEquals(testArrive, flight.getArrivalString());
  }

  @Test
  void test4EmptyWordThrowsException() {
    AirlineRestClient client = newAirlineRestClient();
    String emptyString = "";

    //HttpRequestHelper.RestException ex = assertThrows(HttpRequestHelper.RestException.class, () -> client.addDictionaryEntry(emptyString, emptyString));
    HttpRequestHelper.RestException ex = assertThrows(HttpRequestHelper.RestException.class, () -> client.addFlight(emptyString, 0, emptyString, emptyString, emptyString, emptyString));
    assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
    assertThat(ex.getMessage(), containsString(Messages.missingRequiredParameter(AirlineServlet.AIRLINE_PARAMETER)));
  }

  }
