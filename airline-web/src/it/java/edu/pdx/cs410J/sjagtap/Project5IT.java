package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project5IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project5.class );
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments. Please provide argument as explained below."));
    }

    @Test
    void test2EmptyServer() {
        MainMethodResult result = invokeMain( Project5.class, "-host", HOSTNAME, "-port", PORT, "airline");

        assertThat(result.getTextWrittenToStandardError(), equalTo("** 404Airline airline not found in the dictionary\r\n"));

    }


    @Test
    void test4AddDefinition() {

        MainMethodResult result = invokeMain( Project5.class, "-host", HOSTNAME, "-port", PORT, "airline", "123", "PDX", "07/19/2023", "1:02", "pm", "LAS", "07/19/2023", "6:22", "pm");

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(Messages.definedAirlineAs("airline", "123")));

        result = invokeMain( Project5.class, "-host",HOSTNAME, "-port", PORT, "airline" );

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        Airline airline = new Airline("airline");

        out = result.getTextWrittenToStandardOut();

        assertThat(out, out, containsString("Flight 123 departs 07/19/2023"));

    }
}