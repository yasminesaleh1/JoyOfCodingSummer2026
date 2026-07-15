package edu.pdx.cs.joy.ysaleh;

import com.google.common.annotations.VisibleForTesting;

/**
 * The main class for the Airline Project
 */
public class Project1 {

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
    //Flight flight = new Flight();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    //System.err.println("Missing airline information");


    try {
      /* When no command line arguments are provided, the program should provide a helpful
      message that explains how the program is used */
      if (args[0] == null) {
        // print helpful usage message
        return;
      }

      // README option
      if (args[0].equals("-README") || args[1].equals("-README")) { // may have to rmove the dash
        // print README
        return;
      }

      if (args[0].equals("-print")) {
        printNewFlight = true;
        // accounting for shift in arguments below
        newAirline = args[1];
        newFlightNum = Integer.parseInt(args[2]);
        newSrc = args[3];
        newDepart = args[4] + args[5];   // date & time are separate
        newDest = args[6];
        newArrive = args[7] + args[8];   // date & time are separate
      }
      else {   // no optional arguments included on the command line
        newAirline = args[0];
        // below line may throw NumberFormatException if the str contains non-numeric chars
        newFlightNum = Integer.parseInt(args[1]);
        newSrc = args[2];
        newDepart = args[3] + args[4];   // date & time are separate
        newDest = args[5];
        newArrive = args[6] + args[7];   // date & time are separate
      }

      // error handling below. I got some inspiration from the following code snippet from
      // Google AI overview after googling "how to print thriwn exception message in java":
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
      if (newSrc.length() != 3) {
        throw new IllegalArgumentException("Source airport code entered is not 3 letters long. Source and destination " +
                "airport codes must each be exactly three letters long. \nPlease re-run the program to try again, this " +
                "time with a source airport code that is exactly three letters long.");
      }
      // dest airport not 3 chars
      if (newDest.length() != 3) {
        throw new IllegalArgumentException("Destination airport code entered is not 3 letters long. Source and destination " +
                "airport codes must each be exactly three letters long. \nPlease re-run the program to try again, this " +
                "time with a destination airport code that is exactly three letters long.");
      }
      // src airport contains non-alphabetical characters
      if (!newSrc.matches("[a-zA-Z]+")) {
        // used for help: https://www.geeksforgeeks.org/java/check-if-a-string-contains-only-alphabets-in-java/
        throw new IllegalArgumentException("Source airport code entered contains non-alphabetic characters. Source and " +
                "destination airport codes can only contain 3 alphabetical characters; no numbers or special characters are allowed. " +
                "\nPlease re-run the program to try again, this time entering a source airport code containing only 3 alphabetical characters.");
      }
      // destination airport contains non-alphabetical characters
      if (!newDest.matches("[a-zA-Z]+")) {
        throw new IllegalArgumentException("Destination airport code entered contains non-alphabetic characters. Source and " +
                "destination airport codes can only contain 3 alphabetical characters; no numbers or special characters are allowed. " +
                "\nPlease re-run the program to try again, this time entering a source airport code containing only 3 alphabetical characters.");
      }

      Airline airline = new Airline(newAirline);  // create new airline
      Flight flight = new Flight(newFlightNum, newSrc, newDepart, newDest, newArrive);  // create new flight
      airline.addFlight(flight);

      if (printNewFlight) {
        flight.toString();   // not sure this is correct
      }

    } catch (ArrayIndexOutOfBoundsException e) {
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
    catch (NumberFormatException e) {
      System.out.println("Non-numeric characters were detected in the argument for the flight number. " +
              "The flight number must be a positive integer with no non-numeric characters.");
      System.out.println("Please re-run the program to try again, this time including a flight number" +
              "with integers only.");
    }



    // depart time

    // arrival time

  }

}