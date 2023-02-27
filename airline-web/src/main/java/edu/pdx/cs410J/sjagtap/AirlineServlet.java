package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
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
   * Handles an HTTP GET request from a client by writing the definition of the
   * word specified in the "word" HTTP parameter to the HTTP response.  If the
   * "word" parameter is not specified, all of the entries in the dictionary
   * are written to the HTTP response.
   */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
  {
      response.setContentType( "text/xml" );

      String airline = getParameter(AIRLINE_PARAMETER, request );
      String src = getParameter(SOURCE_PARAMETER, request);
      String dest = getParameter(DEST_PARAMETER, request);

      // what happens if airline is null
      if (airline != null) {
          writeAirline(airline, src, dest, response);
      }
      else
      {
          writeAllAirlineEntries(response);
      }
  }

  /**
   * Handles an HTTP POST request by storing the dictionary entry for the
   * "word" and "definition" request parameters.  It writes the dictionary
   * entry to the HTTP response.
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
      catch(Exception e)
      {
          return;
      }

      Date arriveDate;
      try {
          arriveDate = new SimpleDateFormat("MM/dd/yyyy HH:mm a").parse(arrive);
      }
      catch(Exception e)
      {
          return;
      }

      Airline airlineObject = new Airline(airline);
      SimpleDateFormat date24Format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
      Flight flight = new Flight(Integer.parseInt(flightNumberString), source, new Date(date24Format.format(departDate)), dest, new Date(date24Format.format(arriveDate)));
      airlineObject.addFlight(flight);
      if (this.airlineDictionary.containsKey(airline))
      {
          this.airlineDictionary.get(airline).addFlight(flight);
      }
      else
      {
          this.airlineDictionary.put(airline, airlineObject);
      }

      PrintWriter pw = response.getWriter();
      pw.println(Messages.definedAirlineAs(airline, flight));
      pw.flush();

      response.setStatus( HttpServletResponse.SC_OK);
  }

  /**
   * Handles an HTTP DELETE request by removing all dictionary entries.  This
   * behavior is exposed for testing purposes only.  It's probably not
   * something that you'd want a real application to expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("text/plain");

      this.airlineDictionary.clear();

      PrintWriter pw = response.getWriter();
      pw.println(Messages.allDictionaryEntriesDeleted());
      pw.flush();

      response.setStatus(HttpServletResponse.SC_OK);

  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter( HttpServletResponse response, String parameterName )
      throws IOException
  {
      String message = Messages.missingRequiredParameter(parameterName);
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }

  /**
   * Writes the definition of the given word to the HTTP response.
   *
   * The text of the message is formatted with {@link TextDumper}
   */
  private void writeAirline(String airline, String src, String dest, HttpServletResponse response) throws IOException {
    Airline airlineDefinition = this.airlineDictionary.get(airline);

    if (airlineDefinition == null) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
                // add ignore case here
                if (f.getSource().equals(src) && f.getDestination().equals(dest)) {
                    newAirlineDefinition.addFlight(f);
                }
            }

            if (newAirlineDefinition.getFlights().size() == 0)
            {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
   * Writes all of the dictionary entries to the HTTP response.
   *
   * The text of the message is formatted with {@link TextDumper}
   */
  private void writeAllAirlineEntries(HttpServletResponse response ) throws IOException
  {
      OutputStream ow = response.getOutputStream();
      XmlDumper dumper = new XmlDumper(ow);

      for (Airline a : this.airlineDictionary.values())
      {
          dumper.dump(a);
      }

      response.setStatus( HttpServletResponse.SC_OK );
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

//  @VisibleForTesting
//  String getDefinition(String word) {
//      return this.dictionary.get(word);
//  }
}
