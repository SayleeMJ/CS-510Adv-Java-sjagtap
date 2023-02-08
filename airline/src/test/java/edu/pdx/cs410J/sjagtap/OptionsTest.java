package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;
import java.io.StringWriter;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit tests for the {@link OptionsTest} class.
 * <p>
 * You'll need to update these unit tests as you build out you program.
 */
public class OptionsTest {
    /**
     * Validate help message contains options.
     */
    @Test
    void ValidatePrintReadme() {
        assertThat(Options.getHelpMessage().contains("[options]"), is(true));
    }

    /**
     * Test ValidateGetFlightPretty gets flight object for valid input.
     */
    @Test
    void ValidateGetFlightPretty() {
        Flight flight1 = Options.createAndValidateFlightForPretty(
                "3", "SNN", "01/01/2022 10:10 AM", "SNN", "01/01/2022 11:10 PM");
        assertThat(flight1 != null, is(true));

        flight1 = Options.createAndValidateFlightForPretty(
                "a", "SNN", "01/01/2022 10:10 AM", "SNN", "01/01/2022 11:10 PM");
        assertThat(flight1 == null, is(true));

        flight1 = Options.createAndValidateFlightForPretty(
                "3", "abc", "01/01/2022 10:10 AM", "SNN", "01/01/2022 11:10 PM");
        assertThat(flight1 == null, is(true));

        flight1 = Options.createAndValidateFlightForPretty(
                "3", "SNN", "01/01/2022 24:10 am", "SNN", "01/01/2022 11:10 PM");
        assertThat(flight1 == null, is(true));

        Flight flight = Options.createAndValidateFlightForPretty(
                "3", "SNN", "01/01/2022 10:10 AM", "abc", "01/01/2022 11:10 PM");
        assertThat(flight1 == null, is(true));

        flight1 = Options.createAndValidateFlightForPretty(
                "3", "SNN", "01/01/2022 10:10 AM", "SNN", "01/01/2022 24:10 PM");
        assertThat(flight1 == null, is(true));
    }

    /**
     * Test ValidateGetFlight gets flight object for valid input.
     */
    @Test
    void ValidateCreateAndValidateFlight() {
        Flight flight1 = Options.createAndValidateFlight(
                "3", "SNN", "01/01/2022 10:10", "SNN", "01/01/2022 11:10");
        assertThat(flight1 != null, is(true));

        flight1 = Options.createAndValidateFlight(
                "a", "SNN", "01/01/2022 10:10", "SNN", "01/01/2022 11:10");
        assertThat(flight1 == null, is(true));

        flight1 = Options.createAndValidateFlight(
                "3", "abc1", "01/01/2022 10:10", "SNN", "01/01/2022 11:10");
        assertThat(flight1 == null, is(true));

        flight1 = Options.createAndValidateFlight(
                "3", "SNN", "01/01/2022 24:10", "SNN", "01/01/2022 11:10");
        assertThat(flight1 == null, is(true));

        Flight flight = Options.createAndValidateFlight(
                "3", "SNN", "01/01/2022 10:10", "abc1", "01/01/2022 11:10");
        assertThat(flight1 == null, is(true));

        flight1 = Options.createAndValidateFlight(
                "3", "SNN", "01/01/2022 10:10", "SNN", "01/01/2022 24:10");
        assertThat(flight1 == null, is(true));
    }

    /**
     * Test ValidateGetFlightPretty returns null when arrival time < departure time.
     */
    @Test
    void ValidateGetFlightPrettyArrivalLessThanDeparture() {
        Flight flight1 = Options.createAndValidateFlightForPretty(
                "3", "SNN", "01/02/2022 10:10 AM", "SNN", "01/01/2022 11:10 PM");
        assertThat(flight1 != null, is(false));
    }

    /**
     * Test ValidateGetFlightPretty returns null when airport code is unknown.
     */
    @Test
    void ValidateGetFlightPrettyUnknownAirport() {
        Flight flight1 = Options.createAndValidateFlightForPretty(
                "3", "ADB", "01/02/2022 10:10 AM", "SNN", "01/01/2022 11:10 PM");
        assertThat(flight1 != null, is(false));
    }
}
