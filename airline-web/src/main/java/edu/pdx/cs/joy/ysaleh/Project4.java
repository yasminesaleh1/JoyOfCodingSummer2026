package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.ParserException;
import edu.pdx.cs.joy.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project4 {
    static final String PROGRAM_USAGE = "usage: java -jar target/airline-client.jar [options] <args>\n" +
            "\targs are (in this order):\n" +
            "\t\tairline             The name of the airline\n" +
            "\t\tflightNumber        The flight number\n" +
            "\t\tsrc                 Three-letter code of departure airport\n" +
            "\t\tdepart              Departure date and time (AM/PM)\n" +
            "\t\tdest                Three-letter code of arrival airport\n" +
            "\t\tarrive              Arrival date and time (AM/PM)\n" +
            "\toptions are (options may appear in any order):\n" +
            "\t\t-host hostname      Host computer on which the server runs\n" +
            "\t\t-port port          Port on which the server is listening\n" +
            "\t\t-search             Search for flights\n" +
            "\t\t-print              Prints a description of the new flight\n" +
            "\t\t-README             Prints a README for this project and exits\n" +
            "\tDate and time should be in the format: mm/dd/yyyy hh:mm AM/PM";

    public static final String MISSING_ARGS = "Missing command line arguments";

    /**
     * The main function which runs the client side of the program
     * @param args the command line arguments
     */
    public static void main(String... args) {
        String hostName = null;
        String portString = null;
        String airlineName = null;
        String flightNumString = null;
        int newFlightNum = 0;
        String newSrc = null;
        String newDepart = null;
        String newDest = null;
        String newArrive = null;
        String srcSearch = null;
        String destSearch = null;
        Flight flight = null;
        boolean search = false;
        boolean print = false;
        int i = 0;
        int port = 0;

        try {
            /* When no command line arguments are provided, the program should provide a helpful
      message that explains how the program is used */
            // inspo: https://stackoverflow.com/questions/3868878/java-check-if-command-line-arguments-are-null
            if (args.length == 0) {
                System.err.println(MISSING_ARGS);
                System.err.println("\nWelcome to my Airline Program. This program creates a new airline and flight based on the " +
                        "command line arguments you include. See below for usage of this program:");
                System.err.println(PROGRAM_USAGE);
                return;
            }

                while (i < args.length && args[i].startsWith("-")) {
                switch (args[i]) {
                    case "-host":
                        hostName = args[++i];
                        break;
                    case "-port":
                        portString = args[++i];
                        break;
                    case "-search":
                        search = true;
                        /*airlineName = args[++i];
                        if (i + 2 <= args.length && !(args[i+1].startsWith("-")) ) {
                            srcSearch = args[++i];
                            destSearch = args[++i];

                            // src airport not 3 chars
                            if (!(srcSearch.matches(".{3}"))) {
                                throw new IllegalArgumentException("Source airport code entered is not 3 letters long. Source and destination " +
                                        "airport codes must each be exactly three letters long. \nPlease re-run the program to try again, this " +
                                        "time with a source airport code that is exactly three letters long.");
                            }
                            // dest airport not 3 chars
                            if (!(destSearch.matches(".{3}"))) {
                                throw new IllegalArgumentException("Destination airport code entered is not 3 letters long. Source and destination " +
                                        "airport codes must each be exactly three letters long. \nPlease re-run the program to try again, this " +
                                        "time with a destination airport code that is exactly three letters long.");
                            }
                        }*/
                        break;
                    case "-print":
                        print = true;
                        break;
                    case "-README":
                        System.out.println("README\n---------------------------------------------------");
                        System.out.println("Name: Yasmine Saleh");
                        System.out.println("Course: CS 410: The Joy of Coding with Java and Android");
                        System.out.println("Instructor: David Whitlock");
                        System.out.println("Assignment: Project 4: A REST-ful Airline Web Service ");
                        System.out.println("\nWelcome to my Airline Program. This program creates a new airline and flight based on the " +
                                "command line arguments you include. It supports an airline server that provides REST-ful web services" +
                                " to an airline client, utilizing HTTP-based network communication. You can either add new flights to an " +
                                "airline, search for a specific airline's flights, or search for an airline's flights between two airports. " +
                                "Please see below for usage of this program:");
                        System.out.println(PROGRAM_USAGE);
                        return;
                    default:
                        throw new IllegalArgumentException("Invalid option entered at the beginning of the command line. " +
                                "Please see below for correct usage of this program and do not add any other " +
                                "options/arguments not specified here:\n" + PROGRAM_USAGE);
                }
                ++i;
            }



            if (!search) {  // adding a new flight to server
                if (args.length > i + 10) {  // extraneous arguments detected on command line
                    throw new IllegalArgumentException("Extraneous arguments were detected on the command line. " +
                            "Please see below for correct usage of this program and do not add any other " +
                            "options/arguments not specified here:\n" + PROGRAM_USAGE);
                }
                airlineName = args[i];
                // below line may throw NumberFormatException if the str contains non-numeric chars
                 newFlightNum = Integer.parseInt(args[++i]);
                 newSrc = args[++i];
                 newDepart = args[++i] + " " + args[++i] + " " + args[++i];   // date & time and AM/PM are separate
                 newDest = args[++i];
                 newArrive = args[++i] + " " + args[++i] + " " + args[++i];   // date & time and AM/PM are separate


                // negative flight number
                if (newFlightNum < 0) {
                    throw new IllegalArgumentException("A negative flight number was detected. Flight numbers must be " +
                            "positive integers. \nPlease re-run the program to try again, this time with a positive flight number.");
                }
                // src airport not 3 chars
                if (!(newSrc.matches(".{3}"))) {
                    throw new IllegalArgumentException("Source airport code entered is not 3 letters long. Source and destination " +
                            "airport codes must each be exactly three letters long. \nPlease re-run the program to try again, this " +
                            "time with a source airport code that is exactly three letters long.");
                }
                // src airport contains non-alphabetical characters
                if (!newSrc.matches("[a-zA-Z]+")) {
                    // used for help: https://www.geeksforgeeks.org/java/check-if-a-string-contains-only-alphabets-in-java/
                    throw new IllegalArgumentException("Source airport code entered contains non-alphabetic characters. Source and " +
                            "destination airport codes can only contain 3 alphabetical characters; no numbers or special characters are allowed. " +
                            "\nPlease re-run the program to try again, this time entering a source airport code containing only 3 alphabetical characters.");
                }
                // dest airport not 3 chars
                if (!(newDest.matches(".{3}"))) {
                    throw new IllegalArgumentException("Destination airport code entered is not 3 letters long. Source and destination " +
                            "airport codes must each be exactly three letters long. \nPlease re-run the program to try again, this " +
                            "time with a destination airport code that is exactly three letters long.");
                }
                // destination airport contains non-alphabetical characters
                if (!newDest.matches("[a-zA-Z]+")) {
                    throw new IllegalArgumentException("Destination airport code entered contains non-alphabetic characters. Source and " +
                            "destination airport codes can only contain 3 alphabetical characters; no numbers or special characters are allowed. " +
                            "\nPlease re-run the program to try again, this time entering a source airport code containing only 3 alphabetical characters.");
                }
                // invalid departure date/time
                if (!newDepart.matches("[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4} [0-9]{1,2}:[0-9]{2} (AM|PM)")) {
                    throw new IllegalArgumentException("The flight departure time entered is not valid. The date and time for flight departure/arrival must be entered in the " +
                            "following format as two separate arguments on the command line, with the time in 24-hour format: mm/dd/yyyy hh:mm " +
                            "\nPlease re-run the program to try again, this time entering the departure date and time in the correct format.");
                }
                // invalid arrival date/time
                if (!newArrive.matches("[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4} [0-9]{1,2}:[0-9]{2} (AM|PM)")) {
                    throw new IllegalArgumentException("The flight arrival time entered is not valid. The date and time for flight departure/arrival must be entered in the " +
                            "following format as two separate arguments on the command line, with the time in 24-hour format: mm/dd/yyyy hh:mm " +
                            "\nPlease re-run the program to try again, this time entering the arrival date and time in the correct format.");
                }

                flight = new Flight(newFlightNum, newSrc, newDepart, newDest, newArrive);
            }
            else {
                if (args.length > i+3) {  // -search option called with extraneous arguments
                    throw new IllegalArgumentException("Extraneous arguments were detected on the command line. " +
                            "Please see below for correct usage of this program and do not add any other " +
                            "options/arguments not specified here:\n" + PROGRAM_USAGE);
                }

                airlineName = args[i];
                if (i + 3 == args.length) {
                    srcSearch = args[++i];
                    destSearch = args[++i];

                    // src airport not 3 chars
                    if (!(srcSearch.matches(".{3}"))) {
                        throw new IllegalArgumentException("Source airport code entered is not 3 letters long. Source and destination " +
                                "airport codes must each be exactly three letters long. \nPlease re-run the program to try again, this " +
                                "time with a source airport code that is exactly three letters long.");
                    }
                    // dest airport not 3 chars
                    if (!(destSearch.matches(".{3}"))) {
                        throw new IllegalArgumentException("Destination airport code entered is not 3 letters long. Source and destination " +
                                "airport codes must each be exactly three letters long. \nPlease re-run the program to try again, this " +
                                "time with a destination airport code that is exactly three letters long.");
                    }
                }
            }

            if (hostName != null && portString == null) {
                throw new IllegalArgumentException("A host was given on the command line without a port. If a host is specified" +
                        " on the command line then a port must also be specified. Please run the program again, this time ensuring " +
                        "to add both a host and port.");
            } else if ((hostName == null && portString != null)) {
                throw new IllegalArgumentException("A port was given on the command line without a host. If a port is specified" +
                        " on the command line then a host must also be specified. Please run the program again, this time ensuring " +
                        "to add both a host and port.");
            }

            if (hostName == null && portString == null) {
                hostName = "localhost";
                portString = "8080";
            }
            //port = Integer.parseInt( portString );

        }
        catch (NumberFormatException e) {
            System.out.println("Non-numeric characters were detected in the argument for the flight number. " +
                    "The flight number must be a positive integer with no non-numeric characters.");
            System.out.println("Please re-run the program to try again, this time including a flight number" +
                    "with integers only.");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            // if ArrayIndexOutOfBoundsException was caught when accessing args[] then argument(s) are missing.
            System.out.println("There are arguments missing from the command line. When adding a flight to the server, " +
                    "all the following arguments are required and must be included on the command line in this order: " +
                    "airline name, flight ID number, departure airport code, departure date & time, " +
                    "destination airport code, arrival date & time.");
            System.out.println("Please re-run the program to try again, this time including all the " +
                    "required arguments on the command line when executing the program.");
            return;
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());  // defined above depending on scenario
            return;
        }


        try {
            port = Integer.parseInt( portString );
        } catch (NumberFormatException ex) {
            System.out.println("A non-integer was entered in for the port number. The port number can only be an integer." +
                    " Please run the program again, this time only entering an integer for the port number.");
            return;
        }

        AirlineRestClient client = new AirlineRestClient(hostName, port);

        String message = "";
        try {   // -search option to either pretty-print all airlines or search for flights between 2 airports
            if (search) {
                if (airlineName == null) {
                    throw new IllegalArgumentException("Error: no airline name found. Please re-run the program and enter an airline name with your chosen option.");
                }
                else if (srcSearch != null && destSearch != null) {  // print flights between 2 airports
                    Airline airlineToPrint = client.getAirline(airlineName);
                    StringWriter sw = new StringWriter();
                    PrettyPrinter pretty = new PrettyPrinter(sw);
                    pretty.dump(airlineToPrint, srcSearch, destSearch);
                    message = sw.toString();
                }
                else {    //pretty print all flights in an airline
                    //Map<String, String> dictionary = client.getAllAirlineEntries();
                    Airline airlineToPrint = client.getAirline(airlineName);
                    StringWriter sw = new StringWriter();
                    PrettyPrinter pretty = new PrettyPrinter(sw);
                    pretty.dump(airlineToPrint, null, null);
                    message = sw.toString();
                }
            }
            else {  // add/post a flight to an airline
                client.addFlight(airlineName, newFlightNum, newSrc, newDepart, newDest, newArrive);
                message = Messages.definedNewFlightAs(newFlightNum, newSrc, newDepart, newDest, newArrive);
                if (flight != null && print) {
                    System.out.println(flight.getString());
                }
            }

        } catch (IOException | ParserException ex ) {
            error("It appears that a connection cannot be established. The following message was received while attempting to contact the server: " + ex.getMessage() +
                    "\nPlease try these basic troubleshooting steps and try to re-run the program after: " +
                    "\n\t-Ensure that you have the server side of the program up and running in another terminal " +
                    "(start with mvnw jetty:run) and then re-try running the client side.\n" +
                    "\n\t-Ensure that the port you entered on the command line isn't busy. The default port used (if no other " +
                    "one was entered) is 8080, so ensure that one isn't in use or specify another port that isn't busy on the command line.\n" +
                    "\n\t-If you entered the -host option on the command line, ensure that you entered your host name correctly.\n");
            return;
        } catch (HttpRequestHelper.RestException e) {
            String httpError = String.format("Error: when trying to process your request, the client received a HTTP status code of %d", e.getHttpStatusCode());
            System.err.println(httpError);
            System.err.println("If the error is 404, it is possible that the error was caused by an attempt to search for an airline that wasn't found in the server. " +
                    "In that case, enter the airline into the server before searching for it then try again.");
        }

        System.out.println(message);
    }

    /**
     * A function to print messages to standard error
     * @param message the message to print
     */
    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
    }


}