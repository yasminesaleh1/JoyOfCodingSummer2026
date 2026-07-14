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
    //Flight flight = new Flight();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    System.err.println("Missing airline information");

    // if ArrayIndexOutOfBoundsException was caught when accessing
    // argv[] then argument(s) are missing. need to check for this first

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