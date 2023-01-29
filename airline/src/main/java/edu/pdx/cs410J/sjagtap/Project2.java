package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;

public class Project2 {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Missing command line arguments" + getHelpMessage());
            return;
        }

        String option = args[0];
        switch (option) {
            case "-README":
                printReadMeFile(args);
                break;
            case "-print":
                printUsingCMDLine(args);
                break;
            case "-textFile":
                readAndWrite(args);
                break;
            default:
                System.err.println("Invalid option");

        }
        //Todo -> Validation  of airlineName look through assignment
        //TODO-> Go through assignment
        // Add Test cases
        // Add readme code and update readme
        // update Command line argument
        // Create new jar file
        // what to when file not found
        // TODO -> Handle File not found exception

    }

    /**
     * Reading , creating new flight and write to text file.
     */
    static void readAndWrite(String[] args) {
        if (args.length != 10) {
            System.err.println("Missing command line arguments" + getHelpMessage());
            return;
        }
        String fileName = args[1];
        String airlineName = args[2];
        if (!Airline.isValidAirlineName(airlineName)) {
            System.err.println("Invalid Airline Name");
            return;
        }
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

        // Getting flight details from command line argument
        Flight flightDetails = createAndValidateFlight(flightNumber, src, depart, dst, arrive);
        airline.addFlight(flightDetails);
        try {
            Writer w = new PrintWriter("D://demo2.txt");
            TextDumper textDumper = new TextDumper(w);
            textDumper.dump(airline);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read and print read me file.
     */
    static void printReadMeFile(String[] args) {
        if (args.length == 1) {
            try {
                InputStream readme = Project1.class.getResourceAsStream("README.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
                String line = reader.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = reader.readLine();
                }
            } catch (Exception e) {
                System.err.println("Unable to read README.md");
            }
        } else {
            System.err.println("Missing command line arguments" + getHelpMessage());
        }
    }

    //      0
    // java -jar airline.jar -print "Emerrate99" 42 src 3/15/2023 1:03 dst 3/15/2023 1:00
    static void printUsingCMDLine(String[] args) {
        if (args.length != 9) {
            System.err.println("Missing command line arguments" + getHelpMessage());
            return;
        }

        String airlineName = args[1];
        if (!Airline.isValidAirlineName(airlineName)) {
            System.err.println("Invalid Airline Name");
            return;
        }
        String flightNumber = args[2];
        String src = args[3];
        String depart = args[4] + " " + args[5];
        String dst = args[6];
        String arrive = args[7] + " " + args[8];
        Flight flightDetail = createAndValidateFlight(flightNumber, src, depart, dst, arrive);
        Airline airline1 = new Airline(airlineName);
        airline1.addFlight(flightDetail);

        for (Flight f : airline1.getFlights()) {
            System.out.println("\n" + f.toString());
        }
    }

    /**
     * Returns program help and explanation of inputs.
     *
     * @return String representation of input option.
     */
    @VisibleForTesting
    static String getHelpMessage() {
        return "\n usage: java -jar target/airline-2023.0.0.jar [options] <args>\n" +
                "args are (in this order):\n" +
                "\tairline The name of the airline\n" +
                "\tflightNumber The flight number\n" +
                "\tsrc Three-letter code of departure airport\n" +
                "\tdepart Departure date and time (24-hour time)\n" +
                "\tdest Three-letter code of arrival airport\n" +
                "\tarrive Arrival date and time (24-hour time)\n" +
                "options are (options may appear in any order):\n" +
                "\t-print Prints a description of the new flight\n" +
                "\t-README Prints a README for this project and exits\n" +
                "Date and time should be in the format: mm/dd/yyyy hh:mm";
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
