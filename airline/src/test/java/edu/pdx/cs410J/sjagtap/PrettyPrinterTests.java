package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;
import java.io.StringWriter;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit tests for the {@link PrettyPrinter} class.
 * <p>
 * You'll need to update these unit tests as you build out you program.
 */
public class PrettyPrinterTests {
    /**
     * Validate data gets into pretty format bu dump function.
     */
    @Test
    void validateDataIntoPrettyFormat() {
        StringWriter sw = new StringWriter();
        PrettyPrinter p = new PrettyPrinter(sw);

        Flight flight1 = Options.createAndValidateFlight(
                "3", "SNN", "01/01/2022 10:10", "BJX", "01/01/2022 11:10");
        Airline airline = new Airline("Testing");
        airline.addFlight(flight1);

        p.dump(airline);

        String result = sw.toString();
        assertThat(result.contains("Shannon, Ireland"), is(true));
    }
}
