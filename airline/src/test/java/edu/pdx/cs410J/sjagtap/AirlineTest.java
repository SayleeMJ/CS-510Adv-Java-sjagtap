package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void ValidateFlightsAreSorted() {
        Flight flight1 = Options.createAndValidateFlight(
                "3", "BCD", "01/01/2022 10:10", "ABC", "01/01/2022 11:10");
        Flight flight2 = Options.createAndValidateFlight(
                "2", "ABC", "01/03/2022 10:10", "ABC", "01/01/2022 11:10");
        Flight flight3 = Options.createAndValidateFlight(
                "1", "ABC", "01/01/2022 10:10", "ABC", "01/01/2022 11:10");
        Airline airline = new Airline("Testing");
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        airline.addFlight(flight3);

        List<Flight> list = new ArrayList<>(airline.getFlights()); // covert collection to list.
        assertThat(list.get(0).getNumber() == 1, is(true));
        assertThat(list.get(1).getNumber() == 2, is(true));
        assertThat(list.get(2).getNumber() == 3, is(true));
        assertThat(airline.getName().equals("Testing"), is(true));
    }

    /**
     * Validate airline name.
     */
    @Test
    void ValidateAirlineName() {
        boolean resultValid = Airline.isValidAirlineName("Ab12Cd");
        assertThat(resultValid, is(true)); // Valid scenario.

        boolean resultInvalid = Airline.isValidAirlineName("Ab1$Cd");
        assertThat(resultInvalid, is(false)); // Valid scenario.

    }
}
