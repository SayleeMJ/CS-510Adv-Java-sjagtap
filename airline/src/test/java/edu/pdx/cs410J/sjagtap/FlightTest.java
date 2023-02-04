package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Flight} class.
 * <p>
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {

    /**
     * This unit test will need to be modified (likely deleted) as you implement
     * your project.
     */
//    @Test
//    void getArrivalStringNeedsToBeImplemented() {
//        Flight flight = new Flight(42, "src", null, "dst", null);
//        assertThat(flight.getArrivalString(), equalTo("3/15/2023 1:09"));
//    }

    /**
     * This unit test will need to be modified (likely deleted) as you implement
     * your project.
     */
//    @Test
//    void initiallyAllFlightsHaveTheSameNumber() {
//        Flight flight = new Flight(42, "src", null, "dst", null);
//        assertThat(flight.getNumber(), equalTo(42));
//    }

//    @Test
//    void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
//        Flight flight = new Flight(42, "src", "3/15/2023 1:03", "dst", null);
//        assertThat(flight.getDeparture(), is(nullValue()));
//    }


    @Test
    void validateSourceAndDestination() {
        assertThat(Flight.isValidSrcAndDest("src"), is(true));
        assertThat(Flight.isValidSrcAndDest("source"), is(false));
    }

    @Test
    void validateSourceAndDestinationNumbers() {
        assertThat(Flight.isValidSrcAndDest("s12"), is(false));
        assertThat(Flight.isValidSrcAndDest("s$12"), is(false));
    }

    @Test
    void getFlightNumber() {
        assertThat(Flight.isValidFlightNumber("12"), is(true));
        assertThat(Flight.isValidFlightNumber("1A"), is(false));
    }

//    @Test
//    void getValidDateAndTime() {
//        assertThat(Flight.isValidDateAndTime("1/1/2000 23:00"), is(true));
//        assertThat(Flight.isValidDateAndTime("01/01/2000 1:00"), is(true));
//    }

    @Test
    void getInvalidDateAndTime() {
        assertThat(Flight.isValidDateAndTime("1/1/2000"), is(false));
        assertThat(Flight.isValidDateAndTime("1/1/2000 12"), is(false));
        assertThat(Flight.isValidDateAndTime("1/1/2000 25:00"), is(false));
        assertThat(Flight.isValidDateAndTime("1/1/2000 12:61"), is(false));
    }

}
