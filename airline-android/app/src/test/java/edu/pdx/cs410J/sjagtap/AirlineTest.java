package edu.pdx.cs410J.sjagtap;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 * Unit tests for the {@link Airline} class.
 * <p>
 * You'll need to update these unit tests as you build out you program.
 */
public class AirlineTest {
    /**
     * Validate flight objects are sorted first based on source then based on departure date.
     */
    @Test
    public void ValidateFlightsAreSorted() {
        Flight flight1 = Options.createAndValidateFlightForPretty(
                "3", "JNB", "01/01/2022 10:10 AM", "JNB", "01/02/2022 11:10 PM");
        Flight flight2 = Options.createAndValidateFlightForPretty(
                "2", "BJX", "01/01/2022 10:10 AM", "JNB", "01/02/2022 11:10 AM");
        Flight flight3 = Options.createAndValidateFlightForPretty(
                "1", "BJX", "01/01/2022 10:10 PM", "JNB", "01/02/2022 11:10 AM");
        Airline airline = new Airline("Testing");
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        airline.addFlight(flight3);

        List<Flight> list = new ArrayList<>(airline.getFlights()); // covert collection to list.
        assertThat(list.get(0).getNumber() == 1, is(false));
        assertThat(list.get(1).getNumber() == 2, is(false));
        assertThat(list.get(2).getNumber() == 3, is(true));
        assertThat(airline.getName().equals("Testing"), is(true));
    }

    /**
     * Validate airline name.
     */
    @Test
    public void ValidateAirlineName() {
        boolean resultValid = Airline.isValidAirlineName("Ab12Cd");
        assertThat(resultValid, is(true)); // Valid scenario.

        boolean resultInvalid = Airline.isValidAirlineName("Ab1$Cd");
        assertThat(resultInvalid, is(false)); // Valid scenario.

    }
}