package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL. You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AirlineRestClient {
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final HttpRequestHelper http;


    /**
     * Creates a client to the airline REST service running on the given host and port
     *
     * @param hostName The name of the host
     * @param port     The port
     */
    public AirlineRestClient(String hostName, int port) {
        this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
    }

    @VisibleForTesting
    AirlineRestClient(HttpRequestHelper http) {
        this.http = http;
    }

    public void removeAllDictionaryEntries() throws IOException {
        Response response = http.delete(Map.of());
        throwExceptionIfNotOkayHttpStatus(response);
    }

    private void throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getHttpStatusCode();
        if (code != HTTP_OK) {
            String message = response.getContent();
            throw new RestException(code, message);
        }
    }

    /**
     * This function return flight details for given airline name
     * @param airline provided airline name
     * @return flight details
     */
    public Airline getFlights(String airline) throws IOException, ParserException {
        Response response = http.get(Map.of(AirlineServlet.AIRLINE_PARAMETER, airline));
        throwExceptionIfNotOkayHttpStatus(response);
        String content = response.getContent();
        InputStream stream = new ByteArrayInputStream(content.getBytes(Charset.forName("UTF-8")));

        XmlParser parser = new XmlParser(stream);
        return parser.parse();
    }

    /**
     * Functionality of this function is to return flights for given airline going from specified source to destination
     * @param airline provided airline name
     * @param src provided source
     * @param dest provided destination
     * @return return flights for the given airline name
     */
    public Airline getFlightsFromSourceDestination(String airline, String src, String dest) throws IOException, ParserException
    {
        Response response = http.get(Map.of(AirlineServlet.AIRLINE_PARAMETER, airline, AirlineServlet.SOURCE_PARAMETER, src, AirlineServlet.DEST_PARAMETER, dest));
        throwExceptionIfNotOkayHttpStatus(response);
        String content = response.getContent();

        InputStream stream = new ByteArrayInputStream(content.getBytes
                (Charset.forName("UTF-8")));

        XmlParser parser = new XmlParser(stream);
        return parser.parse();
    }

    /**
     * This function adds flight entry
     * @param airlineName provided airline name
     * @param flightNumber provided flightNumber
     * @param src provided Source
     * @param depart provided departure time and date
     * @param dest provided destination
     * @param arrive provided destination time and date
     */
    public void addFlightEntry(String airlineName, String flightNumber, String src, String depart, String dest, String arrive) throws IOException {
        Response response = http.post(Map.of(AirlineServlet.AIRLINE_PARAMETER, airlineName, AirlineServlet.FLIGHTNUMBER_PARAMETER, flightNumber, AirlineServlet.SOURCE_PARAMETER, src, AirlineServlet.DEPART_PARAMETER, depart, AirlineServlet.DEST_PARAMETER, dest, AirlineServlet.ARRIVE_PARAMETER, arrive));
        throwExceptionIfNotOkayHttpStatus(response);
    }
}
