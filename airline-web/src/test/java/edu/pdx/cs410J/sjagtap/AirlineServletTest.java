package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class AirlineServletTest {

//  @Test
//  void initiallyServletContainsNoDictionaryEntries() throws IOException {
//    AirlineServlet servlet = new AirlineServlet();
//
//    HttpServletRequest request = mock(HttpServletRequest.class);
//    HttpServletResponse response = mock(HttpServletResponse.class);
//    PrintWriter pw = mock(PrintWriter.class);
//
//    when(response.getWriter()).thenReturn(pw);
//
//    servlet.doGet(request, response);
//
//    // Nothing is written to the response's PrintWriter
//    verify(pw, never()).println(anyString());
//    verify(response).setStatus(HttpServletResponse.SC_OK);
//  }

  @Test
  void  addOneFlightToDictionary() throws IOException {
      AirlineServlet servlet = new AirlineServlet();
      String airline = "airline";
      String flightNumber = "1234";
      String src = "SNN";
      String dest = "PDX";
      String depart = "10/10/2023 10:00 AM";
      String arrive = "10/11/2023 10:30 PM";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_PARAMETER)).thenReturn(airline);
    when(request.getParameter(AirlineServlet.FLIGHTNUMBER_PARAMETER)).thenReturn(flightNumber);
    when(request.getParameter(AirlineServlet.SOURCE_PARAMETER)).thenReturn(src);
    when(request.getParameter(AirlineServlet.DEST_PARAMETER)).thenReturn(dest);
    when(request.getParameter(AirlineServlet.DEPART_PARAMETER)).thenReturn(depart);
    when(request.getParameter(AirlineServlet.ARRIVE_PARAMETER)).thenReturn(arrive);


    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);
    assertThat(stringWriter.toString(), containsString(Messages.definedAirlineAs(airline, flightNumber)));


    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));
  }


}
