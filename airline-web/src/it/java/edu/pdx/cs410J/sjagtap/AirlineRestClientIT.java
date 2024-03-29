package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@TestMethodOrder(MethodName.class)
class AirlineRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AirlineRestClient newAirlineRestClient() {
    int port = Integer.parseInt(PORT);
    return new AirlineRestClient(HOSTNAME, port);
  }

  @Test
  void test1EmptyServerContainsNoDictionaryEntries() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();

    HttpRequestHelper.RestException ex =
            assertThrows(HttpRequestHelper.RestException.class, () -> client.getFlights("test"));
    assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
    assertThat(ex.getMessage(), equalTo(Messages.airlineNotFound("test")));
  }

  @Test
  void test1EmptyAirlineParameter() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();

    HttpRequestHelper.RestException ex =
            assertThrows(HttpRequestHelper.RestException.class, () -> client.getFlights(""));
    assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
    assertThat(ex.getMessage(), equalTo(Messages.missingRequiredParameter("airline")));
  }

  @Test
  void test2DefineFlight() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();
    String airline = "TEST AIRLINE";
    String flightNo = "123";
    String src = "PDX";
    String departDate = "07/19/2023 1:02 pm";
    String dest = "ORD";
    String arriveDate = "07/19/2023 6:22 pm";
    client.addFlightEntry(airline, flightNo, src, departDate, dest, arriveDate);

    Airline airlineObj = client.getFlights(airline);
    assertThat(airlineObj.getFlights().size(), equalTo(airlineObj.getFlights().size()));
    assertThat(airlineObj.getName(), equalTo("TEST AIRLINE"));
  }

  // try to add flight with invalid depart date format
  @Test
  void test3InvalidDepartDate() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();
    String airline = "TEST AIRLINE";
    String flightNo = "123";
    String src = "PDX";
    String departDate = "07/19/2023 10:02 pm";
    String dest = "";
    String arriveDate = "07/19/2023 6:22 pm";
    HttpRequestHelper.RestException ex =
            assertThrows(HttpRequestHelper.RestException.class, () ->  client.addFlightEntry(airline, flightNo, src, departDate, dest, arriveDate));
    assertThat(ex.getMessage(),equalTo(Messages.missingRequiredParameter("dest")));
  }

  // try to add flight with invalid arrive date format
  @Test
  void test4InvalidArriveDate() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();
    String airline = "TEST AIRLINE";
    String flightNo = "123";
    String src = "PDX";
    String departDate = "07/19/2023 10:02 pm";
    String dest = "SNN";
    String arriveDate = "";
    HttpRequestHelper.RestException ex =
            assertThrows(HttpRequestHelper.RestException.class, () ->  client.addFlightEntry(airline, flightNo, src, departDate, dest, arriveDate));
    assertThat(ex.getMessage(),equalTo(Messages.missingRequiredParameter("arrive")));
  }

  // try to add flight with empty airline name
  @Test
  void test5EmptyFlightNumber() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();
    String airline = "TEST AIRLINE";
    String flightNo = "";
    String src = "PDX";
    String departDate = "07/19/2023 10:02 pm";
    String dest = "SNN";
    String arriveDate = "07/19/2023 12:22 pm";
    HttpRequestHelper.RestException ex =
            assertThrows(HttpRequestHelper.RestException.class, () ->  client.addFlightEntry(airline, flightNo, src, departDate, dest, arriveDate));
    assertThat(ex.getMessage(),equalTo(Messages.missingRequiredParameter("flightNumber")));
  }
  // try to add flight with empty flight number

  @Test
  void test6EmptyAirlineName() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();
    String airline = "";
    String flightNo = "123";
    String src = "PDX";
    String departDate = "07/19/2023 10:02 pm";
    String dest = "SNN";
    String arriveDate = "07/19/2023 12:22 pm";
    HttpRequestHelper.RestException ex =
            assertThrows(HttpRequestHelper.RestException.class, () ->  client.addFlightEntry(airline, flightNo, src, departDate, dest, arriveDate));
    assertThat(ex.getMessage(),equalTo(Messages.missingRequiredParameter("airline")));
  }

  // try to add flight with empty source

  // try to add flight with empty destination

  // try to get flights with empty source but non empty destination

  // try to get flights with empty destination but non empty source

  // both are empty but there are no flights for that airline

  // both are given, but there no flights

  // both are given, only 1 flight matches

  // both are given, only 2 flight matches

  // get flights for a airline where there is only 1 airline

  // get flights for a airline where there are more than 1 airline

}