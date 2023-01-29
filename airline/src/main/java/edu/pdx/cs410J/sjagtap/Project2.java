package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;

public class Project2 {

    //1. read  from file and get arline
//2. add flight to airline object
//3. write to file
// Class Project2 that contains a main method that optionally reads an Airline from the contents of a text file, creates a new Flight as specified on the command line, adds the Flight to the
//Airline, and then optionally writes the Airline back to the text file. Project2 should have the
//following command line interface:
//// error halnding
//1. file not present
//2. malform
//3. invalid format of flights
    public static void main(String[] args) {
        String option = args[0];
        String fileName = args[1];
        String airlineName = args[2];
        String flightNumber = args[3];
        String src = args[4];
        String depart = args[5] + " " + args[6];
        String dst = args[7];
        String arrive = args[8] + " " + args[9];

        Airline airline;
        //reading details of Airline and Flights from demo.txt and creating new flight
        try {
            Reader r = new FileReader("D://demo.txt");
            TextParser textParser = new TextParser(r);
            airline = textParser.parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParserException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (!isValidOption(args[0])) {
            System.err.println("Invalid option");
            return;
        }
        // Getting flight details from command line argument
        Flight flightDetails = createAndValidateFlight(flightNumber, src, depart, dst, arrive);
        airline.addFlight(flightDetails);

        //Todo -> Validation  of airlineName look through assignment
        //TODO-> Go through assignment
        // Add Test cases
        // Add readme code and update readme
        // update Command line argument
        // Create new jar file
        // TODO -> Handle File not found exception
        //Writing airline and flight details to .txt file
        try {
            Writer w = new PrintWriter("D://demo2.txt");
            TextDumper textDumper = new TextDumper(w);
            textDumper.dump(airline);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static Flight createAndValidateFlight(String flightNumber, String src, String depart, String dst, String arrive) {

        // Validate flight number
        if (!Flight.isValidFlightNumber(flightNumber)) {
            System.err.println("Invalid flight number");
//            throw new IllegalArgumentException("Invalid flight number");
            return null;
        }

        int flightNum = Integer.parseInt(flightNumber);

        // validate src
        if (!Flight.isValidSrcAndDest(src)) {
            System.err.println("Invalid src");
            return null;
        }

        // validate depart
        if (!Flight.isValidDateAndTime(depart)) {
            System.err.println("Invalid depart date");
            return null;
        }

        // validate dest
        if (!Flight.isValidSrcAndDest(dst)) {
            System.err.println("Invalid destination code");
            return null;
        }

        // validate arrive
        if (!Flight.isValidDateAndTime(arrive)) {
            System.err.println("Invalid destination code");
            return null;
        }

        Flight flight = new Flight(flightNum, src, depart, dst, arrive);
        return flight;
    }

    @VisibleForTesting
    static boolean isValidOption(String option) {
        if (option.equals("-print")) {
            return true;
        }

        return false;
    }
}
