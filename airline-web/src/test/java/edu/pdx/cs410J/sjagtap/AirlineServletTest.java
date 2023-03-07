package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class AirlineServletTest {

  @Test
  void addOneToDictionary() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airline = "TEST AIRLINE";
    String flightNo = "123";
    String src = "PDX";
    String departDate = "07/19/2023 1:02 pm";
    String dest = "ORD";
    String arriveDate = "07/19/2023 6:22 pm";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_PARAMETER)).thenReturn(airline);
    when(request.getParameter(AirlineServlet.FLIGHTNUMBER_PARAMETER)).thenReturn(flightNo);
    when(request.getParameter(AirlineServlet.SOURCE_PARAMETER)).thenReturn(src);
    when(request.getParameter(AirlineServlet.FLIGHTNUMBER_PARAMETER)).thenReturn(flightNo);
    when(request.getParameter(AirlineServlet.DEPART_PARAMETER)).thenReturn(departDate);
    when(request.getParameter(AirlineServlet.DEST_PARAMETER)).thenReturn(dest);
    when(request.getParameter(AirlineServlet.ARRIVE_PARAMETER)).thenReturn(arriveDate);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);

    assertThat(stringWriter.toString(), containsString(Messages.definedAirlineAs(airline, flightNo)));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    ServletOutputStream ow = mock(ServletOutputStream.class);
    when(response.getOutputStream()).thenReturn(ow);
    servlet.doGet(request, response);
    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    when(request.getParameter(AirlineServlet.SOURCE_PARAMETER)).thenReturn(null);
    when(request.getParameter(AirlineServlet.DEST_PARAMETER)).thenReturn(null);

    servlet.doGet(request, response);
    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    when(request.getParameter(AirlineServlet.AIRLINE_PARAMETER)).thenReturn(null);
    servlet.doGet(request, response);
  }
}
