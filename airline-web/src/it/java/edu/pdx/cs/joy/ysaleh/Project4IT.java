package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.InvokeMainTestCase;
import edu.pdx.cs.joy.UncaughtExceptionInMain;
import edu.pdx.cs.joy.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * An integration test for {@link Project4} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");


    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    void test2Readme() {
        MainMethodResult result = invokeMain( Project4.class, "-README" );
        assertThat(result.getTextWrittenToStandardOut(), containsString("README"));
    }

    /*@Test
    void test2EmptyServer() {
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(PrettyPrinter.formatFlightCount(0)));
    }

    @Test
    void test3NoDefinitionsThrowsAppointmentBookRestException() {
        String word = "WORD";
        try {
            invokeMain(Project4.class, HOSTNAME, PORT, word);
            fail("Should have thrown a RestException");

        } catch (UncaughtExceptionInMain ex) {
            RestException cause = (RestException) ex.getCause();
            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
        }
    }*/

    @Test
    void test4AddDefinitionAndTestSearchOption() {
        String testAirline = "TEST AIRLINE";
        String testFlightNum = "10";
        int testFlightInt = 10;
        String testSrc = "PDX";
        String testDepart = "03/02/2026 12:57 PM";
        String testDest = "LAX";
        String testArrive = "03/02/2026 2:57 PM";

        MainMethodResult result = invokeMain( Project4.class, "-print", testAirline, testFlightNum, testSrc, "03/02/2026", "12:57", "PM", testDest, "03/02/2026", "2:57", "PM" );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(Messages.definedNewFlightAs(testFlightInt, testSrc, testDepart, testDest, testArrive)));

        result = invokeMain( Project4.class, "-search", testAirline );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("Airline TEST AIRLINE contains"));

        result = invokeMain( Project4.class, "-search", testAirline, "PDX", "LAX" );
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("Flights in Airline TEST AIRLINE that depart from PDX and arrive at LAX"));
    }

    @Test
    void testHttpErrors() {
        MainMethodResult result = invokeMain(Project4.class, "-host", "localhost", "-port", "-3", "-search", "test airline");
        assertThat(result.getTextWrittenToStandardError(), containsString("It appears that a connection cannot be established"));
    }

    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain(Project4.class);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    void testREADME() {
        MainMethodResult result = invokeMain(Project4.class, "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("README"));
    }

    @Test
    void invalidOptionAtBeginningOfCommandLine() {
        MainMethodResult result = invokeMain(Project4.class, "-hi");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Invalid option entered at the beginning of the command line."));
    }

    @Test
    void tooManyCommandLineArgs() {
        MainMethodResult result = invokeMain(Project4.class, "testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM", "hi");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Extraneous arguments were detected on the command line."));
    }

    @Test
    void negativeFlightNumEntered() {
        MainMethodResult result = invokeMain(Project4.class, "testAirline", "-100", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("A negative flight number was detected."));
    }

    @Test
    void srcAirportNotThreeChars() {
        MainMethodResult result = invokeMain(Project4.class, "testAirline", "100", "PDXA", "01/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Source airport code entered is not 3 letters long."));
    }

    @Test
    void srcAirportContainsNonAlphabeticalChars() {
        MainMethodResult result = invokeMain(Project4.class, "testAirline", "100", "P2X", "01/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Source airport code entered contains non-alphabetic characters."));
    }

    @Test
    void destAirportNotThreeChars() {
        MainMethodResult result = invokeMain(Project4.class, "testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "LAXA", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Destination airport code entered is not 3 letters long."));
    }

    @Test
    void destAirportContainsNonAlphabeticalChars() {
        MainMethodResult result = invokeMain(Project4.class, "testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "L2X", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Destination airport code entered contains non-alphabetic characters."));
    }

    @Test
    void invalidDepartureDate() {
        MainMethodResult result = invokeMain(Project4.class, "testAirline", "100", "PDX", "0A/10/2026", "12:16", "PM", "LAX", "01/10/2026", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("The flight departure time entered is not valid."));
    }

    @Test
    void invalidArrivalDate() {
        MainMethodResult result = invokeMain(Project4.class, "testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/202r", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("The flight arrival time entered is not valid."));
    }

    @Test
    void nonNumericCharsInFlightNum() {
        MainMethodResult result = invokeMain(Project4.class, "testAirline", "1oo", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/202r", "2:16", "PM");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Non-numeric characters were detected in the argument for the flight number."));
    }

    @Test
    void argsMissingFromCommandLine() {
        MainMethodResult result = invokeMain(Project4.class, "testAirline", "100", "PDX", "01/10/2026", "12:16", "PM", "LAX", "01/10/202r", "2:16");
        assertThat(result.getTextWrittenToStandardOut(), containsString("There are arguments missing from the command line."));
    }
}