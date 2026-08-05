package edu.pdx.cs.joy.ysaleh;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs.joy.AirportNames;
import edu.pdx.cs.joy.ParserException;

/**
 * The main class for the Airline Project
 */
public class Project3 {
  static final String PROGRAM_USAGE = "usage: java -jar target/airline-1.0.0.jar [options] <args>\n" +
          "\targs are (in this order):\n" +
          "\t\tairline             The name of the airline\n" +
          "\t\tflightNumber        The flight number\n" +
          "\t\tsrc                 Three-letter code of departure airport\n" +
          "\t\tdepart              Departure date and time (AM/PM)\n" +
          "\t\tdest                Three-letter code of arrival airport\n" +
          "\t\tarrive              Arrival date and time (AM/PM)\n" +
          "\toptions are (options may appear in any order):\n" +
          "\t\t-pretty file        Pretty print the airline’s flights to\n" +
          "                        a text file or standard out (file -)" +
          "\t\t-textFile file      Where to read/write the airline info\n" +
          "\t\t-print              Prints a description of the new flight\n" +
          "\t\t-README             Prints a README for this project and exits\n" +
          "\tDate and time should be in the format: mm/dd/yyyy hh:mm";


  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
  }

  public static void main(String[] args) {
    String newAirline = "";
    int newFlightNum = 0;
    String newSrc = "";
    String newDepart = "";
    String newDest = "";
    String newArrive = "";
    boolean printNewFlight = false;
    String fileName = null;
    String prettyFileName = null;
    Airline airline = null;
    int i = 0; // for switch case


    try {
      /* When no command line arguments are provided, the program should provide a helpful
      message that explains how the program is used */
      // inspo: https://stackoverflow.com/questions/3868878/java-check-if-command-line-arguments-are-null
      if (args.length == 0) {
        System.err.println("Missing airline information");
        System.err.println("\nWelcome to my Airline Program. This program creates a new airline and flight based on the " +
                "command line arguments you include. See below for usage of this program:");
        System.err.println(PROGRAM_USAGE);
        return;
      }

      // README option
      /*for (String arg : args) {
        if (arg.equals("-README")) {
          System.out.println("README\n---------------------------------------------------");
          System.out.println("Name: Yasmine Saleh");
          System.out.println("Course: CS 410: The Joy of Coding with Java and Android");
          System.out.println("Instructor: David Whitlock");
          System.out.println("Assignment: Project 3: Pretty-Printing an Airline");
          System.out.println("\nWelcome to my Airline Program. This program creates a new airline and flight based on the " +
                  "command line arguments you include. See below for usage of this program:");
          System.out.println(PROGRAM_USAGE);
          return;
        }
      }*/

      // idea taken from Claude
      while (i < args.length && args[i].startsWith("-")) {
        switch(args[i]) {
          case "-pretty":
            prettyFileName = args[++i];
            break;
          case "-textFile":
            fileName = args[++i];
            break;
          case "-print":
            printNewFlight = true;
            break;
          case "-README":
            System.out.println("README\n---------------------------------------------------");
            System.out.println("Name: Yasmine Saleh");
            System.out.println("Course: CS 410: The Joy of Coding with Java and Android");
            System.out.println("Instructor: David Whitlock");
            System.out.println("Assignment: Project 1: Designing an Airline Application");
            System.out.println("\nWelcome to my Airline Program. This program creates a new airline and flight based on the " +
                    "command line arguments you include. See below for usage of this program:");
            System.out.println(PROGRAM_USAGE);
            return;
          default:
            throw new IllegalArgumentException("Invalid option entered at the beginning of the command line. " +
                    "Please see below for correct usage of this program and do not add any other " +
                    "options/arguments not specified here:\n" + PROGRAM_USAGE);
        }
        ++i;
      }

      if (args.length > i + 10) {  // extraneous arguments detected on command line
        throw new IllegalArgumentException("Extraneous arguments were detected on the command line. " +
                "Please see below for correct usage of this program and do not add any other " +
                "options/arguments not specified here:\n" + PROGRAM_USAGE);
      }

      newAirline = args[i];
      // below line may throw NumberFormatException if the str contains non-numeric chars
      newFlightNum = Integer.parseInt(args[++i]);
      newSrc = args[++i];
      newDepart = args[++i] + " " + args[++i] + " " + args[++i];   // date & time and AM/PM are separate
      newDest = args[++i];
      newArrive = args[++i] + " " + args[++i] + " " + args[++i];   // date & time and AM/PM are separate



      // error handling below. I got some inspiration (mainly from the use of getMessage()) from the following
      // code snippet from Google AI overview after googling "how to print thriwn exception message in java":
      /*
      try {
        throw new IllegalArgumentException("Invalid age entered");
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
          // Output: Invalid age entered
      */


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
      // src airport doesn't correspond to a known airport
      if (AirportNames.getName(newSrc) == null) {
        throw new IllegalArgumentException("Source airport code entered doesn't correspond to a known airport. " +
                "The source and destination airport codes must correspond to real, known airports. \nPlease re-run " +
                "the program to try again, this time entering a source airport code of a real airport.");
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
      // dest airport doesn't correspond to a known airport
      if (AirportNames.getName(newDest) == null) {
        throw new IllegalArgumentException("Destination airport code entered doesn't correspond to a known airport. " +
                "The source and destination airport codes must correspond to real, known airports. \nPlease re-run " +
                "the program to try again, this time entering a destination airport code of a real airport.");
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

      // create new flight with the arguments from the command line
      Flight flight = new Flight(newFlightNum, newSrc, newDepart, newDest, newArrive);

      if (fileName != null) {  // reading from the file (if it was included)
        TextParser tp = new TextParser(fileName);
        airline = tp.parse();
        if (airline == null) { airline = new Airline(newAirline); }  // empty file; newly created
        if (!newAirline.equals(airline.getName())) {
          throw new IllegalArgumentException("The airline name entered on the command line does not match the airline name " +
                  "found in the text file.\nPlease re-run the program, making sure you have entered the correct file name, " +
                  "and that the airline name in the file matches the one entered on the command line.");
        }
        // add command line flight
        airline.addFlight(flight);

        // then dump all into the file using dump()
        TextDumper td = new TextDumper(fileName);
        td.dump(airline);
      }
      else {  // no file specified on command line
        airline = new Airline(newAirline);  // create new airline
        airline.addFlight(flight);
      }

      if (printNewFlight) {   // optional printing of the new flight
        System.out.println(flight.getString());
      }

      if (prettyFileName != null) {
        PrettyPrinter pretty = new PrettyPrinter(prettyFileName);
        pretty.dump(airline);
      }

    } catch (NumberFormatException e) {
      System.out.println("Non-numeric characters were detected in the argument for the flight number. " +
              "The flight number must be a positive integer with no non-numeric characters.");
      System.out.println("Please re-run the program to try again, this time including a flight number" +
              "with integers only.");
    }
    catch (ArrayIndexOutOfBoundsException e) {
      // if ArrayIndexOutOfBoundsException was caught when accessing args[] then argument(s) are missing.
      System.out.println("There are arguments missing from the command line. " +
              "All the following arguments are required and must be included on the command line in this order: " +
              "airline name, flight ID number, departure airport code, departure date & time, " +
              "destination airport code, arrival date & time.");
      System.out.println("Please re-run the program to try again, this time including all the " +
              "required arguments on the command line when executing the program.");
      return;
    }
    catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());  // defined above depending on scenario
      return;
    } catch (ParserException e) {
       System.out.println(e.getMessage());  // defined in TextParser.java
    }


  }

}