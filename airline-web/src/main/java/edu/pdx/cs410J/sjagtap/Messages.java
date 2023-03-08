package edu.pdx.cs410J.sjagtap;

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

    public static String definedAirlineAs(String Airline, Flight flight )
    {
        return String.format( "Airline: %s Flight: %s added to the dictionary", Airline, flight.getNumber());
    }

    public static String definedAirlineAs(String Airline, String flightNumber )
    {
        return String.format( "Airline: %s Flight: %s added to the dictionary", Airline, flightNumber);
    }

    public static String invalidDateFormat( String parameterName )
    {
        return String.format("The required parameter \"%s\" is in invalid format, excepted format MM/dd/yyyy HH:mm a", parameterName);
    }

    public static String airlineNotFound( String airline )
    {
        return String.format("Airline %s not found in the dictionary", airline);
    }

    public static String flightsNotFound( String airline, String src, String dest)
    {
        return String.format("No matching flights found in the dictionary for %s from %s to %s", airline, src, dest);
    }
}