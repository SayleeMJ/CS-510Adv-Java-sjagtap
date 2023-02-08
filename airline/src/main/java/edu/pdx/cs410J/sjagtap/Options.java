package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Options {

    static void processArgs(String[] args) {
        // Validate length of args
        if (args.length == 0) {
            System.err.println("Missing command line arguments. Please provide argument as explained below." + Options.getHelpMessage());
            return;
        }

        // initialize options.
        boolean optionPrint = false;
        boolean optionTextFile = false;
        boolean optionPretty = false;

        String textFileName = null;
        String prettyFile = null;

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

        Airline airlineFromFile = null;
        if (optionTextFile) {
            airlineFromFile = readAndWrite(textFileName, airlineName, flightObject);
        }

        if (optionPrint) {
            if (flightObject != null) {
                Options.printUsingCommandLine(flightObject);
            }
        }

        if (optionPretty) {
            if (airlineFromFile == null) {
                Airline airline = new Airline(airlineName);
                airline.addFlight(flightObject);
                Options.prettyPrint(prettyFile, airline);
            } else {
                Options.prettyPrint(prettyFile, airlineFromFile);
            }

            if (flightObject != null) {
                System.out.println("Pretty printing new object:");
                System.out.println(flightObject.ToStringPretty());
            }
        }
    }
    /**
     * Reading, creating new flight and write to text file.
     *
     * @param args list of command line arguments.
     */
    static Airline readAndWrite(String fileName, String airlineNameFromCmd, Flight flight) {
        File file = new File(fileName);
        Airline airline = null;
        if (file.exists()) {
            try {

                // Read airline info.
                Reader r = new FileReader(fileName);
                TextParser textParser = new TextParser(r);
                airline = textParser.parse();
                String existingAirlineName = airline.getName();

                // Name not matching.
                if (!existingAirlineName.equals(airlineNameFromCmd)) {
                    System.err.println("Airline name is different!");
                    return null;
                }
            } catch (FileNotFoundException e) {
                System.err.println("File does not exists!");
                return null;
            } catch (ParserException e) {
                System.err.println(e.getMessage());
                return null;
            }

            // Add flight
            if (flight != null) {
                airline.addFlight(flight);
            }

            // Write flight
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
                Airline emptyAirline = new Airline(airlineNameFromCmd);
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

        return airline;
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
     * Function for -print option for new parameters
     * Creating a new flight and printing it to the command line
     *
     * @param args list of command line arguments.
     */
    static void printUsingCommandLine(Flight flight) {
        System.out.println(flight.toString());
    }

    static void prettyPrint(String fileName, Airline airline) {
        // Check filename argument
        // if '-' then writer object will point to dump to print it on console
        // if "filename" the writer will point to dump and write flight details to text file

        try {
            Writer writer;
            if (fileName.equals("-")) {
                // Print on console. Point to console stream.
                writer = new PrintWriter(System.out);
            } else {
                // Create file if not present.
                File file = new File(fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }

                writer = new PrintWriter(file);
            }

            // Create dumper and write content.
            PrettyPrinter prettyPrinter = new PrettyPrinter(writer);
            prettyPrinter.dump(airline);
        } catch (Exception e) {
            System.err.println("IO error while writing the content.");
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

    /**
     * validating and creating a new flight
     *
     * @param flightNumber provided flight number.
     * @param src          provided source.
     * @param depart       provided departure time and date.
     * @param dst          provided destination
     * @param arrive       provided arriving time and date.
     */
    static Flight createAndValidateFlight(String flightNumber, String src, String depart, String dst, String arrive) {

        // Validate flight number
        if (!Flight.isValidFlightNumber(flightNumber)) {
            System.err.println("Invalid flight number");
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

        // Covert string to date
        Date arriveDate;
        Date departDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        try {
            departDate = simpleDateFormat.parse(depart);
            arriveDate = simpleDateFormat.parse(arrive);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Flight flight = new Flight(flightNum, src, departDate, dst, arriveDate);
        return flight;
    }


    static Flight createAndValidateFlightForPretty(String flightNumber, String src, String depart, String dst, String arrive) {

        // Validate flight number
        if (!Flight.isValidFlightNumber(flightNumber)) {
            System.err.println("Invalid flight number");
            return null;
        }

        int flightNum = Integer.parseInt(flightNumber);

        // validate src
        if (!Flight.isValidSrcAndDest(src)) {
            System.err.println("Invalid src");
            return null;
        }

         src = src.toUpperCase();

        String source = AirportNames.getName(src);
        if(source == null){
            System.err.println("Source code airport does not exist");
            return null;
        }


        // validate depart
        if (!Flight.isValidDateAndTimeAndZone(depart)) {
            System.err.println("Invalid depart date");
            return null;
        }

        // validate dest
        if (!Flight.isValidSrcAndDest(dst)) {
            System.err.println("Invalid destination code");
            return null;
        }

        dst = dst.toUpperCase();

        String destination = AirportNames.getName(dst);
        if(destination == null){
            System.err.println("Destination airport code does not exist");
            return null;
        }

        // validate arrive
        if (!Flight.isValidDateAndTimeAndZone(arrive)) {
            System.err.println("Invalid destination code");
            return null;
        }

        // convert string to date.
        Date arriveDate;
        Date departDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try {
            departDate = simpleDateFormat.parse(depart);
            arriveDate = simpleDateFormat.parse(arrive);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (arriveDate.before(departDate)) {
            System.err.println("Arrival time is before Departure time");
            return null;
        }

        Flight flight = new Flight(flightNum, src, departDate, dst, arriveDate);
        return flight;
    }
}
