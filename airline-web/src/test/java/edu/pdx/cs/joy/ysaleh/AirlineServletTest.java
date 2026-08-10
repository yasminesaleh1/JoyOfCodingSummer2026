package edu.pdx.cs.joy.ysaleh;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class AirlineServletTest {

  /*@Test
  void initiallyServletContainsNoDictionaryEntries() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    // Nothing is written to the response's PrintWriter
    verify(pw, never()).println(anyString());
    verify(response).setStatus(HttpServletResponse.SC_OK);
  }*/

  @Test
  void addOneWordToDictionary() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String word = "TEST WORD";
    String definition = "TEST DEFINITION";
    String testAirline = "TEST AIRLINE";
    int testFlightNum = 10;
    String testSrc = "PDX";
    String testDepart = "03/02/2026 12:57 PM";
    String testDest = "LAX";
    String testArrive = "03/02/2026 2:57 PM";

    HttpServletRequest request = mock(HttpServletRequest.class);
    //when(request.getParameter(AirlineServlet.WORD_PARAMETER)).thenReturn(word);
    //when(request.getParameter(AirlineServlet.DEFINITION_PARAMETER)).thenReturn(definition);
    when(request.getParameter(AirlineServlet.AIRLINE_PARAMETER)).thenReturn(testAirline);
    when(request.getParameter(AirlineServlet.FLIGHTNUMBER_PARAMETER)).thenReturn(String.valueOf(testFlightNum));
    when(request.getParameter(AirlineServlet.SRC_PARAMETER)).thenReturn(testSrc);
    when(request.getParameter(AirlineServlet.DEPART_PARAMETER)).thenReturn(testDepart);
    when(request.getParameter(AirlineServlet.DEST_PARAMETER)).thenReturn(testDest);
    when(request.getParameter(AirlineServlet.ARRIVE_PARAMETER)).thenReturn(testArrive);


    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    //assertThat(servlet.getDefinition(word), equalTo(definition));
    assertThat(servlet.getAirline(testAirline).getName(), equalTo(testAirline));

    servlet.doGet(request, response);

    assertThat(stringWriter.toString(), containsString(Messages.addedFlight(testAirline, String.valueOf(testFlightNum), testSrc, testDepart, testDest, testArrive)));
  }




}
