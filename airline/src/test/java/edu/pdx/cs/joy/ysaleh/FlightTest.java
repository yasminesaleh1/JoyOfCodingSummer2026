package edu.pdx.cs.joy.ysaleh;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Flight} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {

  @Test
  void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }

  @Test
  void toStringTest() {
    Flight flight = new Flight(200, "PDX", "01/03/2026 12:14 PM", "LAX", "01/13/2026 2:45 PM");
    //System.err.println(flight.getString());
    assertEquals("Flight 200 departs PDX at 1/3/26, 12:14 PM arrives LAX at 1/13/26, 2:45 PM", flight.getString());
  }

  
}
