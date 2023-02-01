package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;

/**
 * The main class for the CS410J airline Project
 */

public class Project2 {

    /**
     * Main entry point of program.
     *
     * @param  args  list of command line arguments.
     */
    public static void main(String[] args) {

        // Validate length of args
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
        //TODO update Command line argument
        //TODO Create new jar file

    }

    /**
     * Reading, creating new flight and write to text file.
     * @param args  list of command line arguments.
     */
    static void readAndWrite(String[] args) {
        if (args.length != 10) {
            System.err.println("Missing command line arguments" + getHelpMessage());
            return;
        }
        String fileName = args[1];
        File file = new File(fileName);
        String airlineName = args[2];
        if (!Airline.isValidAirlineName(airlineName)) {
            System.err.println("Invalid Airline Name");
            return;
        }
        if (file.exists()) {

            String flightNumber = args[3];
            String src = args[4];
            String depart = args[5] + " " + args[6];
            String dst = args[7];
            String arrive = args[8] + " " + args[9];

            Airline airline;

            //reading details of Airline and Flights from filename and creating new flight
            try {
                Reader r = new FileReader(fileName);
                TextParser textParser = new TextParser(r);
                airline = textParser.parse();
                String existingAirlineName = airline.getName();
                if (!existingAirlineName.equals(airlineName)) {
                    System.err.println("Airline name is different!");
                    return;
                }
            } catch (FileNotFoundException e) {
                System.err.println("File does not exists!");
                return;
            } catch (ParserException e) {
                System.err.println(e.getMessage());
                return;
            }

            // Getting flight details from command line argument
            Flight flightDetails = createAndValidateFlight(flightNumber, src, depart, dst, arrive);
            if (flightDetails == null) {
                return;
            }
            airline.addFlight(flightDetails);
            try {
                Writer w = new PrintWriter(fileName);
                TextDumper textDumper = new TextDumper(w);
                textDumper.dump(airline);
            } catch (FileNotFoundException e) {
                System.err.println("File does not exists!");
            }
        } else {
            try {
                // create empty airline object
                Airline emptyAirline = new Airline(airlineName);
                // create file
                file.createNewFile();
                // write airline object contents
                try {
                    Writer w = new PrintWriter(fileName);
                    TextDumper textDumper = new TextDumper(w);
                    textDumper.dump(emptyAirline);
                } catch (FileNotFoundException e) {
                    System.err.println("File does not exists!");
                }


            } catch (IOException e) {
                System.err.println("File have an issue with writing!");
            }
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

    /**
     * Function for -print option
     * Creating a new flight and printing it to the command line
     * @param  args  list of command line arguments.
     */
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
        if (flightDetail == null) {
            return;
        }
        Airline airline1 = new Airline(airlineName);
        airline1.addFlight(flightDetail);

        for (Flight f : airline1.getFlights()) {
            System.out.println("\n" + f.toString());
        }
    }

    /**
     * Returns program help and explanation of inputs.
     * @return      String representation of input option.
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

    /**
     * validating and creating a new flight
     *
     * @param flightNumber   provided flight number.
     * @param src    provided source.
     * @param depart provided departure time and date.
     * @param dst    provided destination
     * @param arrive provided arriving time and date.
     */
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

}
