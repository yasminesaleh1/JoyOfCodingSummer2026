package edu.pdx.cs.joy.ysaleh;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    public static String addedFlight(String name, String flightNum, String src, String depart, String dest, String arrive)
    {
        return String.format( "Added flight #%s from %s on %s to %s on %s to Airline %s", flightNum, name, src, depart, dest, arrive);
    }

    public static String definedNewFlightAs(int flightNum, String src, String depart, String dest, String arrive) {
        return String.format("Defined new flight #%d from %s to %s, leaving on %s and arriving on %s.", flightNum, src, dest, depart, arrive);
    }

}
