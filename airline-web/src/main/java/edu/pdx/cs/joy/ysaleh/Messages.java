package edu.pdx.cs.joy.ysaleh;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    /**
     * Returns a message stating that a parameter is missing
     * @param parameterName the name of the missing parameter
     * @return the message
     */
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    /**
     * Returns a message stating that a flight was added to an airline
     * @param name the name of the airline which the flight was added to
     * @param flightNum the flight number of the new flight
     * @param src the source airport of the new flight
     * @param depart the departure time of the new flight
     * @param dest the destination airport of the new flight
     * @param arrive the arrival time of the new flight
     * @return the message
     */
    public static String addedFlight(String name, String flightNum, String src, String depart, String dest, String arrive)
    {
        return String.format( "Added flight #%s from %s on %s to %s on %s to Airline %s", flightNum, name, src, depart, dest, arrive);
    }

    /**
     * Returns a message stating that a new flight was defined
     * @param flightNum the flight number of the new flight
     * @param src the source airport of the new flight
     * @param depart the departure time of the new flight
     * @param dest the destination airport of the new flight
     * @param arrive the arrival time of the new flight
     * @return the message
     */
    public static String definedNewFlightAs(int flightNum, String src, String depart, String dest, String arrive) {
        return String.format("Defined new flight #%d from %s to %s, leaving on %s and arriving on %s.", flightNum, src, dest, depart, arrive);
    }

}
