package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
     * Initialize object with date and expect specific format from get departure string.
     */
    @Test
    void getDepartureStringNeedsToBeImplemented() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/mm/yyyy hh:mm").parse("3/15/2023 1:09");
        } catch (ParseException e) {
        }
        Flight flight = new Flight(42, "src", date, "dst", date);
        String result = flight.getDepartureString();
        assertThat(result, equalTo("09/03/2023 01:09 AM"));
    }

    /**
     * Initialize object with date and expect specific format fro get arrival string.
     */
    @Test
    void getArrivalStringNeedsToBeImplemented() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/mm/yyyy hh:mm").parse("3/15/2023 1:09");
        } catch (ParseException e) {
        }
        Flight flight = new Flight(42, "src", date, "dst", date);
        String result = flight.getArrivalString();
        assertThat(result, equalTo("09/03/2023 01:09 AM"));
    }

    /**
     * Validate date null.
     */
    @Test
    void initiallyAllFlightsHaveTheSameNumber() {
        Flight flight = new Flight(42, "src", null, "dst", null);
        assertThat(flight.getNumber(), equalTo(42));
    }

    /**
     * Validate GetDepartureTimeReturns Null date.
     */
    @Test
    void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
        Flight flight = new Flight(42, "src", null, "dst", null);
        assertThat(flight.getDeparture(), is(nullValue()));
    }

    /**
     * Validate Source and destination.
     */
    @Test
    void validateSourceAndDestination() {
        assertThat(Flight.isValidSrcAndDest("src"), is(true));
        assertThat(Flight.isValidSrcAndDest("source"), is(false));
    }

    /**
     * Validate Source and destination with numbers.
     */
    @Test
    void validateSourceAndDestinationNumbers() {
        assertThat(Flight.isValidSrcAndDest("s12"), is(false));
        assertThat(Flight.isValidSrcAndDest("s$12"), is(false));
    }

    /**
     * Validate flight numbers.
     */
    @Test
    void getFlightNumber() {
        assertThat(Flight.isValidFlightNumber("12"), is(true));
        assertThat(Flight.isValidFlightNumber("1A"), is(false));
    }

    /**
     * Validate date time 24 hours format.
     */
    @Test
    void getValidDateAndTime24hours() {
        assertThat(Flight.isValidDateAndTime24Hours("1/1/2000 23:00"), is(true));
        assertThat(Flight.isValidDateAndTime24Hours("01/01/2000 1:00"), is(true));
    }

    /**
     * Validate date time 24 hours format.
     */
    @Test
    void getInvalidDateAndTime24Hours() {
        assertThat(Flight.isValidDateAndTime24Hours("1/1/2000"), is(false));
        assertThat(Flight.isValidDateAndTime24Hours("1/1/2000 12"), is(false));
        assertThat(Flight.isValidDateAndTime24Hours("1/1/2000 25:00"), is(false));
        assertThat(Flight.isValidDateAndTime24Hours("1/1/2000 12:61"), is(false));
    }

    /**
     * Validate date time 12 hours format.
     */
    @Test
    void getValidDateAndTime12hours() {
        assertThat(Flight.isValidDateAndTimeAndZone12Hour("1/1/2000 01:00 am"), is(true));
        assertThat(Flight.isValidDateAndTimeAndZone12Hour("01/01/2000 11:00 pm"), is(true));
    }

    /**
     * Validate date time 12 hours format.
     */
    @Test
    void getInvalidDateAndTime12Hours() {
        assertThat(Flight.isValidDateAndTimeAndZone12Hour("1/1/2000 23:00"), is(false));
        assertThat(Flight.isValidDateAndTimeAndZone12Hour("01/01/2000 1:00"), is(false));
    }
}
