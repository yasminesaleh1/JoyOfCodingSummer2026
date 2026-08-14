package edu.pdx.cs.joy.ysaleh;

import edu.pdx.cs.joy.AbstractFlight;

public class Flight extends AbstractFlight {
    private String source;  //  Three-letter code of departure airport
    private String destination;  //  Three-letter code of departure airport
    private String departure;
    private String arrival;
    private int id;
    private String originalDepartureTime;
    private String originalArrivalTime;


    /**
     * Default constructor. Just here to prevent errors.
     */
    public Flight() {}

    /**
     * Argument constructor. Initializes fields for a new Flight object with specified parameters.
     * Inspiration was taken from the following code snippet provided by Google AI overview
     * after I searched "java localdatetime parse from string":
     * ```
     * import java.time.LocalDateTime;
     * import java.time.format.DateTimeFormatter;
     *
     * public class Main {
     *     public static void main(String[] args) {
     *         String customString = "29/07/2026 14:30:15";
     *
     *         // Define the pattern matching your text
     *         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
     *
     *         // Parse with the formatter
     *         LocalDateTime dateTime = LocalDateTime.parse(customString, formatter);
     *
     *         System.out.println(dateTime); // Output: 2026-07-29T14:30:15
     *     }
     * }
     * ```
     * @param flightNum the identification number for the flight
     * @param src the three-letter code of the source airport
     * @param depart the date and time of flight departure
     * @param dest the three-letter code of the departure airport
     * @param arrive the date and time of flight departure
     */
    public Flight(int flightNum, String src, String depart, String dest, String arrive) {
        departure = depart;
        arrival = arrive;
        id = flightNum;
        source = src.toUpperCase();
        destination = dest.toUpperCase();
    }


    /**
     * Getter function for Flight identification number
     * @return the Flight object's ID number
     */
    @Override
    public int getNumber() {
        return id;
    }

    /**
     * Getter function for Flight source airport code
     * @return the Flight object's source airport code
     */
    @Override
    public String getSource() { return source; }

    /**
     * Getter function for Flight departure date and time.
     * Reference: https://www.geeksforgeeks.org/java/localdatetime-format-method-in-java/
     * @return the Flight object's date and time of departure
     */
    @Override
    public String getDepartureString() {
        return departure;
    }

    /**
     * Getter function for Flight destination airport code
     * @return the Flight object's destination airport code
     */
    @Override
    public String getDestination() { return destination; }

    /**
     * Getter function for Flight arrival date and time
     * Reference: https://www.geeksforgeeks.org/java/localdatetime-format-method-in-java/
     * @return the Flight object's date and time of arrival
     */
    @Override
    public String getArrivalString() {
        return arrival;
    }

    /**
     * getString() method which calls super's already-implemented toString() method
     * @return the string representing the Flight object
     */
    public String getString() { return super.toString(); }

}
