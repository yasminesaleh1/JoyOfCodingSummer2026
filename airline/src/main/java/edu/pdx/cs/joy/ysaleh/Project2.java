package edu.pdx.cs.joy.ysaleh;

import com.google.common.annotations.VisibleForTesting;

/**
 * The main class for the Airline Project
 */
public class Project2 {
  static final String PROGRAM_USAGE = "usage: java -jar target/airline-1.0.0.jar [options] <args>\n" +
          "\targs are (in this order):\n" +
          "\t\tairline The name of the airline\n" +
          "\t\tflightNumber The flight number\n" +
          "\t\tsrc Three-letter code of departure airport\n" +
          "\t\tdepart Departure date and time (24-hour time)\n" +
          "\t\tdest Three-letter code of arrival airport\n" +
          "\t\tarrive Arrival date and time (24-hour time)\n" +
          "\toptions are (options may appear in any order):\n" +
          "\t\t-print Prints a description of the new flight\n" +
          "\t\t-README Prints a README for this project and exits\n" +
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
      if (args[0].equals("-README") || args[1].equals("-README")) {
        System.out.println("README\n---------------------------------------------------");
        System.out.println("Name: Yasmine Saleh");
        System.out.println("Course: CS 410: The Joy of Coding with Java and Android");
        System.out.println("Instructor: David Whitlock");
        System.out.println("Assignment: Project 1: Designing an Airline Application");
        System.out.println("\nWelcome to my Airline Program. This program creates a new airline and flight based on the " +
                "command line arguments you include. See below for usage of this program:");
        System.out.println(PROGRAM_USAGE);
        return;
      }


      if (args[0].equals("-print")) {
        if (args.length > 9) {   // extraneous arguments detected on command line
          throw new IllegalArgumentException("Extraneous arguments were detected on the command line. " +
                  "Please see below for correct usage of this program and do not add any other " +
                  "options/arguments not specified here:\n" + PROGRAM_USAGE);
        }
        printNewFlight = true;
        // accounting for shift in arguments below
        newAirline = args[1];
        newFlightNum = Integer.parseInt(args[2]);
        newSrc = args[3];
        newDepart = args[4] + " " + args[5];   // date & time are separate
        newDest = args[6];
        newArrive = args[7] + " " + args[8];   // date & time are separate
      }
      // unknown option entered with a dash at the beginning of the command line
      else if (args[0].matches("-.*")) {
        throw new IllegalArgumentException("Invalid option entered at the beginning of the command line. " +
                "Please see below for correct usage of this program and do not add any other " +
                "options/arguments not specified here:\n" + PROGRAM_USAGE);
      }
      else {   // no optional arguments included on the command line
        if (args.length > 8) {   // extraneous arguments detected on command line
          throw new IllegalArgumentException("Extraneous arguments were detected on the command line. " +
                  "Please see below for correct usage of this program and do not add any other " +
                  "options/arguments not specified here:\n" + PROGRAM_USAGE);
        }
        newAirline = args[0];
        // below line may throw NumberFormatException if the str contains non-numeric chars
        newFlightNum = Integer.parseInt(args[1]);
        newSrc = args[2];
        newDepart = args[3] + " " + args[4];   // date & time are separate
        newDest = args[5];
        newArrive = args[6] + " " + args[7];   // date & time are separate
      }

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


      // airline name is too short
      if (newAirline.length() <= 2) {
        throw new IllegalArgumentException("Airline name entered is too short. " +
                "The airline name cannot be shorter than 3 character. \nPlease re-run " +
                "the program to try again, this time with an airline name that is at least 3 characters long.");
      }
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
      // The following dates and times are valid: 7/15/2026 10:39 and 06/2/2026 1:03
      // That is, the month and the day can be expressed as either 1 or 2 digits. The year should always be four digits.
      if (!newDepart.matches("[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4} [0-9]{1,2}:[0-9]{2}")) {
        throw new IllegalArgumentException("The flight departure time entered is not valid. The date and time for flight departure/arrival must be entered in the " +
                "following format as two separate arguments on the command line, with the time in 24-hour format: mm/dd/yyyy hh:mm " +
                "\nPlease re-run the program to try again, this time entering the departure date and time in the correct format.");
      }
      // invalid arrival date/time
      if (!newArrive.matches("[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4} [0-9]{1,2}:[0-9]{2}")) {
        throw new IllegalArgumentException("The flight arrival time entered is not valid. The date and time for flight departure/arrival must be entered in the " +
                "following format as two separate arguments on the command line, with the time in 24-hour format: mm/dd/yyyy hh:mm " +
                "\nPlease re-run the program to try again, this time entering the arrival date and time in the correct format.");
      }

      // creating the new airline and flight
      Airline airline = new Airline(newAirline);  // create new airline
      Flight flight = new Flight(newFlightNum, newSrc, newDepart, newDest, newArrive);  // create new flight
      airline.addFlight(flight);

      if (printNewFlight) {   // optional printing of the new flight
        System.out.println(flight.getString());
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
    }




    // depart time

    // arrival time

  }

}