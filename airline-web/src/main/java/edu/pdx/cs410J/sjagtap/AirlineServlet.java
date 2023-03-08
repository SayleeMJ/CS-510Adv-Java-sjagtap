package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.
 */
public class AirlineServlet extends HttpServlet {
    static final String AIRLINE_PARAMETER = "airline";

    static final String SOURCE_PARAMETER = "src";

    static final String DEST_PARAMETER = "dest";

    static final String DEPART_PARAMETER = "depart";

    static final String ARRIVE_PARAMETER = "arrive";

    static final String FLIGHTNUMBER_PARAMETER = "flightNumber";

    private final Map<String, Airline> airlineDictionary = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the list of flights of the
     * airline specified in the "airline" HTTP parameter to the HTTP response.  Note:
     * airline parameter is a required parameter.
     * Other optional parameters are "src" and "dest". Providing these will get the list
     * of flights from source to destination.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/xml" );

        String airline = getParameter(AIRLINE_PARAMETER, request );
        String src = getParameter(SOURCE_PARAMETER, request);
        String dest = getParameter(DEST_PARAMETER, request);

        if (airline != null) {
            writeAirline(airline, src, dest, response);
        }
        else
        {
            // if airline is null, throw error
            missingRequiredParameter(response, AIRLINE_PARAMETER);
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "airline" and other request parameters ("src", "dest", "depart", "arrive",
     * "flightNumber". It writes the dictionary entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );

        String airline = getParameter(AIRLINE_PARAMETER, request );
        if ( airline == null) {
            missingRequiredParameter(response, AIRLINE_PARAMETER);
            return;
        }

        String source = getParameter(SOURCE_PARAMETER, request );
        if ( source == null) {
            missingRequiredParameter( response, SOURCE_PARAMETER );
            return;
        }

        String dest = getParameter(DEST_PARAMETER, request );
        if ( dest == null) {
            missingRequiredParameter( response, DEST_PARAMETER );
            return;
        }

        String depart = getParameter(DEPART_PARAMETER, request );
        if ( depart == null) {
            missingRequiredParameter( response, DEPART_PARAMETER );
            return;
        }

        String arrive = getParameter(ARRIVE_PARAMETER, request );
        if ( arrive == null) {
            missingRequiredParameter( response, ARRIVE_PARAMETER );
            return;
        }

        String flightNumberString = getParameter(FLIGHTNUMBER_PARAMETER, request );
        if ( flightNumberString == null) {
            missingRequiredParameter( response, FLIGHTNUMBER_PARAMETER );
            return;
        }

        Date departDate;
        try {
            departDate = new SimpleDateFormat("MM/dd/yyyy HH:mm a").parse(depart);
        }
        catch(ParseException e)
        {
            dateInvalidFormat( response, DEPART_PARAMETER );
            return;
        }

        Date arriveDate;
        try {
            arriveDate = new SimpleDateFormat("MM/dd/yyyy HH:mm a").parse(arrive);
        }
        catch(ParseException e)
        {
            dateInvalidFormat( response, ARRIVE_PARAMETER );
            return;
        }


        SimpleDateFormat date24Format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Flight flight = new Flight(Integer.parseInt(flightNumberString), source, new Date(date24Format.format(departDate)), dest, new Date(date24Format.format(arriveDate)));

        if (this.airlineDictionary.containsKey(airline))
        {
            this.airlineDictionary.get(airline).addFlight(flight);
        }
        else
        {
            Airline airlineObject = new Airline(airline);
            airlineObject.addFlight(flight);
            this.airlineDictionary.put(airline, airlineObject);
        }

        PrintWriter pw = response.getWriter();
        pw.println(Messages.definedAirlineAs(airline, flight));
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes an error message about an invalid date parameter to the HTTP response.
     * The text of the error message is created by {@link Messages#invalidDateFormat(String)}
     */
    private void dateInvalidFormat( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.invalidDateFormat(parameterName);
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Writes the list of flights of the given airline to the HTTP response.
     * The xml of the message is formatted with {@link XmlDumper}
     */
    private void writeAirline(String airline, String src, String dest, HttpServletResponse response) throws IOException {
        Airline airlineDefinition = getFlights(airline);

        if (airlineDefinition == null) {
            String message = Messages.airlineNotFound(airline);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, message);
        }

        else {
            OutputStream ow = response.getOutputStream();
            XmlDumper dumper = new XmlDumper(ow);

            if ( src == null && dest != null )
            {
                missingRequiredParameter(response, SOURCE_PARAMETER);
            }
            else if ( src != null && dest == null)
            {
                missingRequiredParameter(response, DEST_PARAMETER);
            }
            else if (src != null && dest != null) {
                Airline newAirlineDefinition = new Airline(airlineDefinition.getName());
                for (Flight f : airlineDefinition.getFlights()) {
                    if (f.getSource().equals(src) && f.getDestination().equals(dest)) {
                        newAirlineDefinition.addFlight(f);
                    }
                }

                if (newAirlineDefinition.getFlights().size() == 0)
                {
                    String message = Messages.flightsNotFound(airline, src, dest);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, message);
                }
                else {
                    dumper.dump(newAirlineDefinition);
                }
            } else {
                dumper.dump(airlineDefinition);
            }

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

    @VisibleForTesting
    Airline getFlights(String airline) {
        return this.airlineDictionary.get(airline);
    }
}