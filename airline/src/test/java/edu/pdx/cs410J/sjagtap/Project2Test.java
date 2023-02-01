package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Project2Test {
    @Test
    void readmeCanBeReadAsResource() throws IOException {
        try (
                InputStream readme = Project2.class.getResourceAsStream("README.txt")
        ) {
            assertThat(readme, not(nullValue()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line = reader.readLine();
            assertThat(line, containsString("This is a README file!"));

        }
    }

    @Test
    void getValidAirlineName() {
        assertThat(Airline.isValidAirlineName("Indigo"), is(true));
        assertThat(Airline.isValidAirlineName("Indigo123"), is(true));
    }

    @Test
    void getInvalidAirlineName() {
        assertThat(Airline.isValidAirlineName("Indigo$"), is(false));
        assertThat(Airline.isValidAirlineName("Indigo123$"), is(false));
    }
}
