package edu.pdx.cs410J.sjagtap;

import java.io.PrintStream;
import java.security.InvalidParameterException;

import static edu.pdx.cs410J.sjagtap.Options.parseAndProcessArgs;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

    public static void main(String... args) {
        try {
            parseAndProcessArgs(args);
        } catch (InvalidParameterException exception) {
            error("Error while parsing input parameters: " + exception.getMessage());
        }

    }

    private static void error(String message) {
        PrintStream err = System.err;
        err.println("** " + message);
    }
}