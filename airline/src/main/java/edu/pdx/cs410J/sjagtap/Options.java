package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Options {

    /**
     * Reading, creating new flight and write to text file.
     *
     * @param args list of command line arguments.
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
     *
     * @param args list of command line arguments.
     */
    static void printUsingCMDLine(String[] args) {
        if (args.length != 9) {
            System.err.println("An argument is malformed.");
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

        //Can I add these flights to array or list?

        for (Flight f : airline1.getFlights()) {
            System.out.println("\n" + f.toString());
        }
    }

    /**
     * Function for -print option for new parameters
     * Creating a new flight and printing it to the command line
     *
     * @param args list of command line arguments.
     */
    static void printUsingCommandLine(String[] args) {
        if (args.length != 11) {
            System.err.println("An argument is malformed.");
            return;
        }
        String airlineName = args[1];
        if (!Airline.isValidAirlineName(airlineName)) {
            System.err.println("Invalid Airline Name");
            return;
        }

        String flightNumber = args[2];
        String src = args[3];
        String depart = args[4] + " " + args[5] + " " + args[6];
        String dst = args[7];
        String arrive = args[8] + " " + args[9] + " " + args[10];
        Flight flightDetail = createAndValidateFlightForPretty(flightNumber, src, depart, dst, arrive);
        if (flightDetail == null) {
            return;
        }
        Airline airline1 = new Airline(airlineName);
        airline1.addFlight(flightDetail);

        //Can I add these flights to array or list?

        for (Flight f : airline1.getFlights()) {
            System.out.println("\n" + f.toString());
        }
    }

    static void prettyPrint(String[] args) {
        //TODO --> Check filename argument
        // if '-' then writer object will point to dump to print it on console
        // if "filename" the writer will point to dump and write flight details to text file

        if (args.length != 12) {
            System.err.println("Missing command line arguments" + getHelpMessage());
            return;
        }

        String airlineName = args[2];
        if (!Airline.isValidAirlineName(airlineName)) {
            System.err.println("Invalid Airline Name");
            return;
        }

        String fileName = args[1];
        File file = new File(fileName);

        if (file.exists()) {
            String flightNumber = args[3];
            String src = args[4];
            String depart = args[5] + " " + args[6] + " " + args[7];
            String dst = args[8];
            String arrive = args[9] + " " + args[10] + " " + args[11];

            Airline airline = new Airline(airlineName);

            Flight flightDetails = createAndValidateFlightForPretty(flightNumber, src, depart, dst, arrive);
            if (flightDetails == null) {
                return;
            }

            Flight flightDetails2 = createAndValidateFlightForPretty("1", src, depart, dst, arrive);
            Flight flightDetails3 = createAndValidateFlightForPretty("2", src, depart, dst, arrive);
            Flight flightDetails4 = createAndValidateFlightForPretty("3", src, depart, dst, arrive);
            airline.addFlight(flightDetails);
            airline.addFlight(flightDetails);
            try {
                Writer w = new PrintWriter(file);
                PrettyPrinter prettyPrinter = new PrettyPrinter(w);
                prettyPrinter.dump(airline);
            } catch (FileNotFoundException e) {
                System.err.println("File does not exists!");
            }
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

        // covert string to date
        //depart string to date
        //arrive string to date
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
        if (!Flight.isValidDateAndTimeAndZone(depart)) {
            System.err.println("Invalid depart date");
            return null;
        }

        // validate dest
        if (!Flight.isValidSrcAndDest(dst)) {
            System.err.println("Invalid destination code");
            return null;
        }

        // validate arrive
        if (!Flight.isValidDateAndTimeAndZone(arrive)) {
            System.err.println("Invalid destination code");
            return null;
        }

        // covert string to date
        //depart string to date
        //arrive string to date
        Date arriveDate;
        Date departDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try {
            departDate = simpleDateFormat.parse(depart);
            arriveDate = simpleDateFormat.parse(arrive);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Flight flight = new Flight(flightNum, src, departDate, dst, arriveDate);
        return flight;
    }

}
