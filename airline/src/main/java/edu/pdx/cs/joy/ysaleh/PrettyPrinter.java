package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.AirlineDumper;
import edu.pdx.cs.joy.AirportNames;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;

/**
 * PrettyPrinter class which prints an airline's flights in a nicely-formatter manner, to either a file or STDOUT.
 * Resembles my TextDumper class very closely; much of the code in this class is borrowed from there.
 */
public class PrettyPrinter implements AirlineDumper<Airline> {
    private final File file;

    /**
     * Argument constructor. Initializes fields for a new PrettyPrinter object with specified parameters.
     * @param fileName the name of the file to parse
     */
    public PrettyPrinter(String fileName) {  // constructor
        if (fileName.equals("-")) { file = null; }  // print to stdout
        else { file = new File(fileName); }
    }


    /**
     * pretty-printing dump method; writes all the flights in an airline to a file or to STDOUT, in a nicely-formatted manner.
     * @param airline
     *        The airline being written to a destination
     *
     */
    public void dump(Airline airline) {
        String sourceName;
        String destinationName;
        long flightDuration;
        //int longFormat = DateFormat.LONG;  // for printing the dates
        //DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(longFormat, longFormat);

        try {
            if (file == null) {  //stdout
                System.out.println("\nAirline: " + airline.getName());
                System.out.println("————————————————————————");

                // print flight info line-by-line, for each flight in the airline
                for (Flight airlineFlight : airline.getFlights()) {
                    sourceName = AirportNames.getName((airlineFlight.getSource()).toUpperCase());
                    destinationName = AirportNames.getName((airlineFlight.getDestination()).toUpperCase());
                    flightDuration = airlineFlight.calculateDuration();

                    // Flight #123: PDX --> LAX
                    System.out.println("\nFlight #" + airlineFlight.getNumber() + ": "
                            + airlineFlight.getSource() + " --> " + airlineFlight.getDestination());

                    System.out.println("\tSource Airport:      " + sourceName);
                    System.out.println("\tDestination Airport: " + destinationName);
                    System.out.println("\tDeparture Time:      " + airlineFlight.getDepartureString());
                    System.out.println("\tArrival Time:        " + airlineFlight.getArrivalString());
                    System.out.println("\tFlight Duration:     " + flightDuration + " minutes");
                }
            }

            else {
                if (!file.exists()) {
                    file.createNewFile();
                }

                // got this style of file I/O from the koans in intermediate/AboutFileIO.java
                FileWriter fw = new FileWriter(file);
                PrintWriter pw = new PrintWriter(fw);

                pw.println("\nAirline: " + airline.getName());
                pw.println("————————————————————————");


                // print flight info line-by-line, for each flight in the airline
                for (Flight airlineFlight : airline.getFlights()) {
                    sourceName = AirportNames.getName((airlineFlight.getSource()).toUpperCase());
                    destinationName = AirportNames.getName((airlineFlight.getDestination()).toUpperCase());
                    flightDuration = airlineFlight.calculateDuration();

                    // Flight #123: PDX --> LAX
                    pw.println("\nFlight #" + airlineFlight.getNumber() + ": "
                            + airlineFlight.getSource() + " --> " + airlineFlight.getDestination());

                    pw.println("\tSource Airport:      " + sourceName);
                    pw.println("\tDestination Airport: " + destinationName);
                    pw.println("\tDeparture Time:      " + airlineFlight.getDepartureString());
                    pw.println("\tArrival Time:        " + airlineFlight.getArrivalString());
                    pw.println("\tFlight Duration:     " + flightDuration + " minutes");
                }
                pw.flush();
                pw.close();
            }

        }
        catch (IOException e) {
            System.out.println("Error when trying to open file. The file cannot be opened or created. " +
                    "Check to make sure you entered a valid file name (not a directory name) and try again.");
            return;
        }
    }

}
