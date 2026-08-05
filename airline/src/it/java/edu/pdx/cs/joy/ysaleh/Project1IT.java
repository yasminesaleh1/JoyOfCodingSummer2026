package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.InvokeMainTestCase;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * An integration test for the {@link Project3} main class.
 */
class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing airline information"));
  }

  @Test
  void testREADME() {
      MainMethodResult result = invokeMain("-README");
      assertThat(result.getTextWrittenToStandardOut(), containsString("README"));
  }

  @Test
  void invalidOptionAtBeginningOfCommandLine() {
      MainMethodResult result = invokeMain("-hi");
      assertThat(result.getTextWrittenToStandardOut(), containsString("Invalid option entered at the beginning of the command line."));
  }

    @Test
    void tooManyCommandLineArgs() {
        MainMethodResult result = invokeMain("testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM", "hi");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Extraneous arguments were detected on the command line."));
    }

    @Test
    void negativeFlightNumEntered() {
        MainMethodResult result = invokeMain("testAirline", "-100", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("A negative flight number was detected."));
    }

    @Test
    void srcAirportNotThreeChars() {
        MainMethodResult result = invokeMain("testAirline", "100", "PDXA", "01/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Source airport code entered is not 3 letters long."));
    }

    @Test
    void srcAirportContainsNonAlphabeticalChars() {
        MainMethodResult result = invokeMain("testAirline", "100", "P2X", "01/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Source airport code entered contains non-alphabetic characters."));
    }

    @Test
    void srcAirportDoesntCorrespondToAKnownAirport() {
        MainMethodResult result = invokeMain("testAirline", "100", "UUU", "01/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Source airport code entered doesn't correspond to a known airport."));
    }

    @Test
    void destAirportNotThreeChars() {
        MainMethodResult result = invokeMain("testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "LAXA", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Destination airport code entered is not 3 letters long."));
    }

    @Test
    void destAirportContainsNonAlphabeticalChars() {
        MainMethodResult result = invokeMain("testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "L2X", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Destination airport code entered contains non-alphabetic characters."));
    }

    @Test
    void destAirportDoesntCorrespondToAKnownAirport() {
        MainMethodResult result = invokeMain("testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "III", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Destination airport code entered doesn't correspond to a known airport."));
    }

    @Test
    void invalidDepartureDate() {
        MainMethodResult result = invokeMain("testAirline", "100", "PDX", "0A/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("The flight departure time entered is not valid."));
    }

    @Test
    void invalidArrivalDate() {
        MainMethodResult result = invokeMain("testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/202r", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("The flight arrival time entered is not valid."));
    }

    @Test
    void nonNumericCharsInFlightNum() {
        MainMethodResult result = invokeMain("testAirline", "1oo", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/202r", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Non-numeric characters were detected in the argument for the flight number."));
    }

    @Test
    void argsMissingFromCommandLine() {
        MainMethodResult result = invokeMain("testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/202r", "2:16");
        assertThat(result.getTextWrittenToStandardOut(), containsString("There are arguments missing from the command line."));
    }

    @Test
    void validAirportFlightIsPrinted() {
        MainMethodResult result = invokeMain("-print", "testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 100 departs PDX at 1/10/26, 12:16 PM arrives LAX at 1/10/26, 2:16 PM"));
    }

    @Test
    void prettyPrintToStandardOut() {
        MainMethodResult result = invokeMain("-pretty", "-", "testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight #100: PDX --> LAX"));
    }

    @Test
    void prettyPrintToFile() throws IOException {
        String testFile = "ysaleh_prettyprint_IT_test";
        MainMethodResult result = invokeMain("-pretty", "ysaleh_prettyprint_IT_test", "testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM");
        // inspired by example from Google AI after searching "how to extract first line of a file in java"
        BufferedReader br = new BufferedReader(new FileReader(testFile));
        for (int i = 0; i < 4; ++i) { br.readLine(); } // read first four lines so we can get to the 5th one
        String text = br.readLine();
        assertThat(text, Matchers.containsString("Flight #100: PDX --> LAX"));
    }


}