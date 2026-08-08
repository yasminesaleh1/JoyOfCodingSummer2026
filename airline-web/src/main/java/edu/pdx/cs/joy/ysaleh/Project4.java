package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.ParserException;

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

    public static void main(String... args) {
        String hostName = null;
        String portString = null;
        String airlineName = null;
        String flightNumString = null;
        String srcSearch = null;
        String destSearch = null;
        boolean search = false;
        boolean print = false;
        int i = 0;
        int port;

        while (i < args.length && args[i].startsWith("-")) {
            switch(args[i]) {
                case "-host":
                    hostName = args[++i];
                    break;
                case "-port":
                    portString = args[++i];
                    break;
                case "-search":
                    search = true;
                    airlineName = args[++i];
                    if (i + 2 <= args.length) {
                        srcSearch = args[++i];
                        destSearch = args[++i];
                    }
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
                            " to an airline client, utilizing HTTP-based network communication. See below for usage of this program:");
                    System.out.println(PROGRAM_USAGE);
                    return;
                default:
                    throw new IllegalArgumentException("Invalid option entered at the beginning of the command line. " +
                            "Please see below for correct usage of this program and do not add any other " +
                            "options/arguments not specified here:\n" + PROGRAM_USAGE);
            }
            ++i;
        }

        if (hostName != null && portString == null) {
            throw new IllegalArgumentException("A host was given on the command line without a port. If a host is specified" +
                    " on the command line then a port must also be specified. Please run the program again, this time ensuring " +
                    "to add both a host and port.");
        }
        else if ((hostName == null && portString != null)) {
            throw new IllegalArgumentException("A port was given on the command line without a host. If a port is specified" +
                    " on the command line then a host must also be specified. Please run the program again, this time ensuring " +
                    "to add both a host and port.");
        }


        if (hostName == null && portString == null) {
            hostName = "localhost";
            portString = "8080";
        }


        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("A non-integer was entered in for the port number. The port number can only be an integer." +
                    " Please run the program again, this time only entering an integer for the port number.");
            return;
        }

        AirlineRestClient client = new AirlineRestClient(hostName, port);

        String message;
        try {
            //pretty print all flights in an airline
            if (search) {
                if (airlineName == null) {
                    throw new IllegalArgumentException("Error");
                }
                else {
                    message = PrettyPrinter.formatDictionaryEntry(word, client.getDefinition(word));
                }
            }

            if (word == null) {
                // Print all word/definition pairs
                Map<String, String> dictionary = client.getAllAirlineEntries();
                StringWriter sw = new StringWriter();
                PrettyPrinter pretty = new PrettyPrinter(sw);
                pretty.dump(dictionary);
                message = sw.toString();

            } else if (definition == null) {
                // Print all dictionary entries
                message = PrettyPrinter.formatDictionaryEntry(word, client.getDefinition(word));

            } else {
                // Post the word/definition pair
                client.addDictionaryEntry(word, definition);
                message = Messages.definedWordAs(word, definition);
            }

        } catch (IOException | ParserException ex ) {
            error("While contacting server: " + ex.getMessage());
            return;
        }

        System.out.println(message);
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
    }


}