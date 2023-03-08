package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * A unit test for the REST client that demonstrates using mocks and
 * dependency injection
 */
public class AirlineRestClientTest {

  @Test
  void getFlightsPerformsHttpGetWithAirlineParameter() throws IOException, ParserException{
    Airline qatar = new Airline("Qatar");
    Flight flight = new Flight(123, "PDX", convertStringToDate("07/19/2023 1:02 pm"), "ORD", convertStringToDate("07/19/2023 6:22 pm"));
    qatar.addFlight(flight);

    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(eq(Map.of("airline", "Qatar")))).thenReturn(airlineAsText(qatar));

    AirlineRestClient client = new AirlineRestClient(http);

    Airline qatarReturned = client.getFlights("Qatar");
    assertThat(qatarReturned.getName(), equalTo("Qatar"));
    assertThat(qatarReturned.getFlights().size(), equalTo(1));
  }

  @Test
  void getFlightsFromSrcDestinationPerformsHttpGetWithAirlineSrcDestParameter() throws IOException, ParserException{
    Airline qatar = new Airline("Qatar");
    Flight flight = new Flight(123, "PDX", convertStringToDate("07/19/2023 1:02 pm"), "ORD", convertStringToDate("07/19/2023 6:22 pm"));
    qatar.addFlight(flight);

    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.get(eq(Map.of("airline", "Qatar", "src", "PDX", "dest", "ORD")))).thenReturn(airlineAsText(qatar));

    AirlineRestClient client = new AirlineRestClient(http);

    Airline qatarReturned = client.getFlightsFromSrcDestination("Qatar", "PDX", "ORD");
    assertThat(qatarReturned.getName(), equalTo("Qatar"));
    assertThat(qatarReturned.getFlights().size(), equalTo(1));
  }

  @Test
  void addFlightEntryPerformsHttpPostWithParameters() throws IOException, ParserException{
    HttpRequestHelper http = mock(HttpRequestHelper.class);
    when(http.post(eq(Map.of("airline", "Qatar", "flightNumber", "123", "src", "PDX",
            "depart", "07/19/2023 1:02 pm", "dest" , "ORD", "arrive", "07/19/2023 6:22 pm")))).thenReturn(new HttpRequestHelper.Response("blah"));

    AirlineRestClient client = new AirlineRestClient(http);

    client.addFlightEntry("Qatar", "123","PDX", "07/19/2023 1:02 pm", "ORD","07/19/2023 6:22 pm");
  }

  private Date convertStringToDate(String dateString)
  {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Date date;
    try {
      date = simpleDateFormat.parse(dateString);
    } catch (ParseException e) {
      return null;
    }

    return date;
  }

  private HttpRequestHelper.Response airlineAsText(Airline airline) throws IOException{
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    new XmlDumper(stream).dump(airline);

    return new HttpRequestHelper.Response(stream.toString());
  }
}