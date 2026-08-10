package edu.pdx.cs.joy.ysaleh;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


public class FlightTest {

    @Test
    void toStringTest() {
        Flight flight = new Flight(200, "PDX", "01/03/2026 12:14 PM", "LAX", "01/13/2026 2:45 PM");
        //System.err.println(flight.getString());
        assertEquals("Flight 200 departs PDX at 01/03/2026 12:14 PM arrives LAX at 01/13/2026 2:45 PM", flight.getString());
    }


    @Test
    void testFlightGetters() {
        Flight f = new Flight(781, "LAX", "09/27/2026 12:14 PM", "JFK", "09/27/2026 6:45 PM");
        assertEquals(781, f.getNumber());
        assertEquals("LAX", f.getSource());
        assertEquals("09/27/2026 12:14 PM", f.getDepartureString());
        assertEquals("JFK", f.getDestination());
        assertEquals("09/27/2026 6:45 PM", f.getArrivalString());
    }

}
