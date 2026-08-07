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

    public static String addedFlight(int flightNum, String src, String dest, String name)
    {
        return String.format( "Added flight #%d from %s to %s to Airline %s", flightNum, src, dest, name );
    }
    /* public static String definedWordAs(String word, String definition )
    {
        return String.format( "Defined %s as %s", word, definition );
    } */

    public static String allDictionaryEntriesDeleted() {
        return "All dictionary entries have been deleted";
    }

}
