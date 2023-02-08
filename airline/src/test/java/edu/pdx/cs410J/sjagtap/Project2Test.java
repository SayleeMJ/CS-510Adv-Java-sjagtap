package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * A unit test for code in the <code>Project2</code> class.
 */
public class Project2Test
{
    /**
     * Validate readme resource.
     */
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

    /**
     * Validate airline name.
     */
    @Test
    void getValidAirlineName() {
        assertThat(Airline.isValidAirlineName("Indigo"), is(true));
        assertThat(Airline.isValidAirlineName("Indigo123"), is(true));
    }

    /**
     * Validate airline name.
     */
    @Test
    void getInvalidAirlineName() {
        assertThat(Airline.isValidAirlineName("Indigo$"), is(false));
        assertThat(Airline.isValidAirlineName("Indigo123$"), is(false));
    }

    /**
     * Validate airline name.
     */
    @Test
    void validateMain() {
        String[] args = new String[0];
        Project2.main(args);
    }

    /**
     * Validate airline -pretty -
     */
    @Test
    void validateMainPretty() {
        try {
            String[] args = new String[]{
                    "-textFile",
                    "testingFile.txt",
                    "Indigo",
                    "70",
                    "SCL",
                    "10/10/2023",
                    "1:30",
                    "am",
                    "SNN",
                    "10/10/2023",
                    "2:30",
                    "am"
            };
            Project2.main(args);
            Project2.main(args);
            Project2.main(args);
        } catch (Exception e) {
            assertThat(true, equalTo("File write failures occurred"));
        }
    }
}
