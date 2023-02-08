package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Project3 {

    /**
     * Main entry point of program.
     *
     * @param args list of command line arguments.
     */
    public static void main(String[] args) {

        // Validate length of args
        if (args.length == 0) {
            System.err.println("Missing command line arguments. Please provide argument as explained below." + Options.getHelpMessage());
            return;
        }

        // initialize options.
        boolean optionPrint = false;
        boolean optionTextFile = false;
        boolean optionPretty = false;

        String textFileName;
        String prettyFile;

        int i = 0;
        while (i < args.length) {

            if (args[i].equals("-README")) {
                Options.printReadMeFile(args);
                return;
            }

            if (args[i].equals("-print")) {
                optionPrint = true;
                i++;
                continue;
            }

            if (args[i].equals("-textFile")) {
                optionTextFile = true;

                if (i + 1 >= args.length) {
                    System.err.println("Invalid argument. Please provide text file name to read and write.");
                    return;
                }

                textFileName = args[i + 1];
                i = i + 2;
                continue;
            }

            if (args[i].equals("-pretty")) {
                optionPretty = true;

                if (i + 1 >= args.length) {
                    System.err.println("Invalid argument. Please provide text file name to write pretty flight information.");
                    return;
                }

                prettyFile = args[i + 1];
                i = i + 2;
                continue;
            }

            break;
        }

        String airlineName = null;
        Flight flightObject;
        int remainingNumberOfArgument = args.length - i;
        if (remainingNumberOfArgument == 10) { // Check new argument in new date format
            airlineName = args[i];
            String flightNumber = args[i + 1];
            String srcAirport = args[i + 2];
            String departDate = args[i + 3] + " " + args[i + 4] + " " + args[i + 5];
            String dstAirport = args[i + 6];
            String arriveDate = args[i + 7] + " " + args[i + 8] + " " + args[i + 9];

            flightObject = Options.createAndValidateFlightForPretty(flightNumber, srcAirport, departDate, dstAirport, arriveDate);
        } else if (remainingNumberOfArgument == 8) { // Check argument in old date format
            airlineName = args[i];
            String flightNumber = args[i + 1];
            String srcAirport = args[i + 2];
            String departDate = args[i + 3] + " " + args[i + 4];
            String dstAirport = args[i + 5];
            String arriveDate = args[i + 6] + " " + args[i + 7];
            flightObject = Options.createAndValidateFlight(flightNumber, srcAirport, departDate, dstAirport, arriveDate);
        } else {
            System.err.println("Invalid number of argument present. Please check readme.");
            return;
        }

        if (!Airline.isValidAirlineName(airlineName)) {
            System.err.println("Invalid Airline Name");
            return;
        }

        if (optionTextFile) {
            // ToDo: Call text file function
        }

        if (optionPrint) {
            if (flightObject != null) {
                Options.printUsingCommandLine(flightObject);
            }
        }

        if (optionPretty) {
            // ToDo: Call print pretty function
        }
    }
}
