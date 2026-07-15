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
      } else {   // no optional arguments included on the command line
        newAirline = args[0];
        // below line may throw NumberFormatException if the str contains non-numeric chars
        newFlightNum = Integer.parseInt(args[1]);
        newSrc = args[2];
        newDepart = args[3] + args[4];   // date & time are separate
        newDest = args[5];
        newArrive = args[6] + args[7];   // date & time are separate
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

    // airline name length should be sufficient

    // newFlightNum should be int, otherwise it would throw type exception (?)

    // src should be 3 letters and shouldn't contain numbers

    // depart time

    // dest should be 3 letters and shouldn't contain numbers

    // arrival time

    for (String arg : args) {
      System.out.println(arg);
    }
  }

}