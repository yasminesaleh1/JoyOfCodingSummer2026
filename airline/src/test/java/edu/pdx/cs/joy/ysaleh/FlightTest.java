package edu.pdx.cs.joy.ysaleh;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Flight} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {

  @Test
  void toStringTest() {
    Flight flight = new Flight(200, "PDX", "01/03/2026 12:14 PM", "LAX", "01/13/2026 2:45 PM");
    //System.err.println(flight.getString());
    assertEquals("Flight 200 departs PDX at 1/3/26, 12:14 PM arrives LAX at 1/13/26, 2:45 PM", flight.getString());
  }

  @Test
  void compareMethodSortsFlightsCorrectly() {
    Flight f1 = new Flight(781, "LAX", "09/27/2026 12:14 PM", "JFK", "09/27/2026 6:45 PM");
    Flight f2 = new Flight(781, "PDX", "09/27/2026 12:14 PM", "JFK", "09/27/2026 6:00 PM");
    Flight f3 = new Flight(781, "PDX", "09/27/2026 1:14 PM", "JFK", "09/27/2026 7:00 PM");
    Flight f4 = new Flight(781, "PDX", "09/27/2026 1:14 PM", "LAX", "09/27/2026 3:00 PM");
    assertTrue(f1.compareTo(f2) < 0);  // f1 before f2
    assertTrue(f2.compareTo(f1) > 0);  // f2 after f1
    assertTrue(f2.compareTo(f3) < 0);  // f2 before f3
    assertEquals(0, f3.compareTo(f4));
  }

  @Test
  void testFlightGetters() {
    Flight f = new Flight(781, "LAX", "09/27/2026 12:14 PM", "JFK", "09/27/2026 6:45 PM");
    assertEquals(781, f.getNumber());
    assertEquals("LAX", f.getSource());
    assertEquals("9/27/26, 12:14 PM", f.getDepartureString());
    assertEquals("09/27/2026 12:14 PM", f.getOriginalDepartureTime());
    assertEquals("JFK", f.getDestination());
    assertEquals("9/27/26, 6:45 PM", f.getArrivalString());
    assertEquals("09/27/2026 6:45 PM", f.getOriginalArrivalTime());
  }

  @Test
  void flightDurationsAreCalculatedCorrectly() {
    Flight f = new Flight(781, "PDX", "03/01/2026 2:00 PM", "DFW", "03/01/2026 6:00 PM");
    // 2:00pm to 6:00pm is exactly 4 hours, which is 240 minutes (4 hrs * 60 mins per hour)
    assertEquals(240, f.calculateDuration());
  }
  
}
