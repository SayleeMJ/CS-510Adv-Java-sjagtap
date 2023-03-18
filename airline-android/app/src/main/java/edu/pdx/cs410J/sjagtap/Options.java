package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;
// import edu.pdx.cs410J.ParserException;
// import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Options class to handle each operation passed as command line argument.
 */
public class Options {

    /**
     * Read from file, creating new file and write to airline information xml file.
     *
//     * @param xmlFileName  name of xml file to write.
     * @param airlineName  airline name from user.
     * @param flightObject flight object created from command line values.
     * @return new Airline object.
     */

    public static void readAndWriteToXmlFile(File file, String airlineName, Flight flightObject) {
        // Changed this function to take file as input insted of file name as we create file in android app.
        Airline airline = null;
        if (file.exists()) {
            try {
                // Read airline info.
                InputStream inputStream = new FileInputStream(file);
                XmlParser xmlParser = new XmlParser(inputStream);
                airline = xmlParser.parse();


                // Name not matching.
                String existingAirlineName = airline.getName();
                if (!existingAirlineName.equals(airlineName)) {
                    throw new IllegalArgumentException("Airline name is different!");
                }
            } catch (ParserException p) {
                System.err.println(p.getMessage());
                return;
            } catch (Exception e) {
                System.err.println("Error While Reading. " + e.getMessage());
                return;
            }

            if(flightObject != null) {
                airline.addFlight(flightObject);
            }
            // write to xml file
            try {
                OutputStream outputStream = new FileOutputStream(file);
                XmlDumper xmlDumper = new XmlDumper(outputStream);
                xmlDumper.dump(airline);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return;
            }
        } else {
            try {
                Airline emptyAirline = new Airline(airlineName);
//                boolean newFile = file.createNewFile();
                file.createNewFile();
                // write airline object contents
                try {
                    OutputStream outputStream = new FileOutputStream(file);
                    XmlDumper xmlDumper = new XmlDumper(outputStream);
                    xmlDumper.dump(emptyAirline);
                } catch (FileNotFoundException e) {
                    System.err.println("File does not exists!");
                }
            } catch (IOException e) {
                System.err.println("Enable to create file since its not present.");
            }
        }
    }

    /**
     * Gets command line argument. Perform validation. Generate flight object and call appropriate
     * function to perform further operations.
     *
     * @param args command line arguments.
     */
//    static void parseAndProcessArgs(String[] args) {
//        // Validate length of args
//        if (args.length == 0) {
//            usage("Missing command line arguments. Please provide argument as explained below.");
//            return;
//        }
//
//        // initialize options.
//        boolean optionPrint = false;
//        boolean optionSearchSpecific = false;
//        boolean optionSearch = false;
//        boolean optionPost = false;
//
//        String hostName = null;
//        String portString = null;
//        int port = 0000;
//
//        int i = 0;
//        while (i < args.length) {
//
//
//            if (args[i].equals("-README")) {
//                Options.printReadMeFile(args);
//                return;
//            }
//
//            if (args[i].equals("-print")) {
//                optionPrint = true;
//                i++;
//                continue;
//            }
//
//            if (args[i].equals("-search")) {
//                optionSearch = true;
//                i++;
//                continue;
//            }
//
//            if (args[i].equals("-host")) {
//                if (i + 1 >= args.length) {
//                    usage("Invalid argument. Please provide hostname on which the server is running");
//                    return;
//                }
//
//                hostName = args[i + 1];
//                i = i + 2;
//                continue;
//            }
//
//            if (args[i].equals("-port")) {
//                if (i + 1 >= args.length) {
//                    usage("Invalid argument. Please provide port on which the server is listening");
//                    return;
//                }
//
//                portString = args[i + 1];
//
//                try {
//                    port = Integer.parseInt(portString);
//
//                } catch (NumberFormatException ex) {
//                    usage("Invalid argument. Please provide port as a integer.");
//                    return;
//                }
//
//                i = i + 2;
//                continue;
//            }
//
//            break;
//        }
//
////        if (hostName == null || portString == null) {
////            usage("Hostname and or port cannot be empty.");
////            return;
////        }
//
//        String airlineName = null;
//        String srcAirport = null;
//        String dstAirport = null;
//        String flightNumber = null;
//        String departDate = null;
//        String arriveDate = null;
//        Flight flightObject = null;
//        int remainingNumberOfArgument = args.length - i;
//        if (remainingNumberOfArgument == 1) {
//            if(!optionSearch){
//                usage("Missing -search tag");
//            }
//            airlineName = args[i];
//        } else if (remainingNumberOfArgument == 3) {
//            optionSearchSpecific = true;
//            if (!optionSearch) {
//                usage("Missing -search tag");
//            }
//            airlineName = args[i];
//            srcAirport = args[i + 1];
//            dstAirport = args[i + 2];
//            validateInputParameters(srcAirport, dstAirport);
//        } else if (remainingNumberOfArgument == 10) {
//            airlineName = args[i];
//            flightNumber = args[i + 1];
//            srcAirport = args[i + 2];
//            departDate = args[i + 3] + " " + args[i + 4] + " " + args[i + 5];
//            dstAirport = args[i + 6];
//            arriveDate = args[i + 7] + " " + args[i + 8] + " " + args[i + 9];
//
//            if (hostName != null || portString != null) {
//                optionPost = true;
//            }
//
//            flightObject = createAndValidateFlightForPretty(flightNumber, srcAirport, departDate, dstAirport, arriveDate);
//
//        } else {
//            usage("Invalid number of argument present. Please check readme.");
//            return;
//        }
//
//        if (!Airline.isValidAirlineName(airlineName)) {
//            usage("Invalid Airline Name");
//            return;
//        }
//
//        if (optionPrint) {
//            if (flightObject != null) {
//                Options.printUsingCommandLine(flightObject);
//            }
//        }
//
//
//        AirlineRestClient client = new AirlineRestClient(hostName, port);
//
//        String message = null;
//        try {
//            if (optionSearch) {
//                if (hostName == null || portString == null) {
//                    usage("Hostname and or port cannot be empty.");
//                    return;
//                }
//                Airline airline = client.getFlights(airlineName);
//                Writer sw = new StringWriter();
//                PrettyPrinter pretty = new PrettyPrinter(sw);
//                pretty.dump(airline);
//                message = sw.toString();
//            }
//
//            if (optionSearchSpecific && optionSearch) {
//                if (hostName == null || portString == null) {
//                    usage("Hostname and or port cannot be empty.");
//                    return;
//                }
//                Airline airline = client.getFlightsFromSrcDestination(airlineName, srcAirport, dstAirport);
//                StringWriter sw = new StringWriter();
//                PrettyPrinter pretty = new PrettyPrinter(sw);
//                pretty.dump(airline);
//                message = sw.toString();
//            }
//
//            if (optionPost && optionPrint) {
//                if (hostName == null || portString == null) {
//                    usage("Hostname and or port cannot be empty.");
//                    return;
//                }
//                client.addFlightEntry(airlineName, flightNumber, srcAirport, departDate, dstAirport, arriveDate);
//            }
//
//            if (optionPost && !optionPrint) {
//                if (hostName == null || portString == null) {
//                    usage("Hostname and or port cannot be empty.");
//                    return;
//                }
//                client.addFlightEntry(airlineName, flightNumber, srcAirport, departDate, dstAirport, arriveDate);
//                message = Messages.definedAirlineAs(airlineName, flightNumber);
//            }
//
//        } catch (IOException | ParserException ex) {
//            error("While contacting server: " + ex.getMessage());
//            return;
//        } catch (HttpRequestHelper.RestException exception) {
//            error(exception.getHttpStatusCode() + exception.getMessage());
//            return;
//        }
//
//        if (message != null) {
//            System.out.println(message);
//        }
//    }

    /**
     * Function for -print option for new parameters.
     * Print flights to console.
     *
     * @param flightObject Flight information.
     */
    private static void printUsingCommandLine(Flight flightObject) {
        System.out.println(flightObject.toString());
    }


    /**
     * Validating the input parameters provided to POST api.
     *
     * @param src provided source.
     * @param dst provided destination
     */
    static void validateInputParameters(String src, String dst) {

        // validate src
        if (!Flight.isValidSrcAndDest(src)) {
            usage("Invalid source airport code.");
        }

        src = src.toUpperCase();
        String source = AirportNames.getName(src);
        if (source == null) {
            usage("The three-letter source airport code does not correspond to a known airport");
        }

        // validate dest
        if (!Flight.isValidSrcAndDest(dst)) {
            usage("Invalid destination airport code.");
        }

        dst = dst.toUpperCase();

        String destination = AirportNames.getName(dst);
        if (destination == null) {
            usage("The three-letter destination airport code does not correspond to a known airport");
        }
    }

    /**
     * validating and creating a new flight for 12 hours date format.
     *
     * @param flightNumber provided flight number.
     * @param src          provided source.
     * @param depart       provided departure time and date.
     * @param dst          provided destination
     * @param arrive       provided arriving time and date.
     */
    static Flight createAndValidateFlightForPretty(String flightNumber, String src, String depart, String dst, String arrive) {

        // Validate flight number
        if (!Flight.isValidFlightNumber(flightNumber)) {
           throw new IllegalArgumentException("Invalid flight number.");
        }

        int flightNum = Integer.parseInt(flightNumber);

        // validate src
        if (!Flight.isValidSrcAndDest(src)) {
            throw new IllegalArgumentException("Invalid source airport code.");
        }

        src = src.toUpperCase();
        String source = AirportNames.getName(src);
        if (source == null) {
//            usage();
            throw new IllegalArgumentException("The three-letter source airport code does not correspond to a known airport");
        }

        // validate depart. chekc this
        if (!Flight.isValidDateAndTimeAndZone12Hour(depart)) {
//            usage();
            throw new IllegalArgumentException("Invalid departure date-time format.");
        }

        // validate dest
        if (!Flight.isValidSrcAndDest(dst)) {
//            usage();
            throw new IllegalArgumentException("Invalid destination airport code.");
        }

        dst = dst.toUpperCase();

        String destination = AirportNames.getName(dst);
        if (destination == null) {
//            usage();
            throw new IllegalArgumentException("The three-letter destination airport code does not correspond to a known airport");
        }

        // validate arrive. check this
        if (!Flight.isValidDateAndTimeAndZone12Hour(arrive)) {
//            usage();
            throw new IllegalArgumentException("Invalid arrival date-time format.");
        }

        // convert string to date.
        Date arriveDate;
        Date departDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try {
            departDate = simpleDateFormat.parse(depart);
            arriveDate = simpleDateFormat.parse(arrive);
        } catch (ParseException e) {
//            System.err.println();
            throw new IllegalArgumentException("Invalid date-time format.");
        }

        if (arriveDate.before(departDate)) {
//            System.err.println();
            throw new IllegalArgumentException("Arrival datetime is before departure datetime.");
        }

        Flight flight = new Flight(flightNum, src, departDate, dst, arriveDate);
        return flight;
    }

    /**
     * Validating and creating a new flight for with 24 hours date format.
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
            throw new IllegalArgumentException("Invalid flight number");
        }

        int flightNum = Integer.parseInt(flightNumber);

        // validate src
        if (!Flight.isValidSrcAndDest(src)) {
            throw new IllegalArgumentException("Invalid source airport code.");
        }
        src = src.toUpperCase();
        String source = AirportNames.getName(src);
        if (source == null) {
            throw new IllegalArgumentException("The three-letter source airport code does not correspond to a known airport");
        }

        // validate depart
        if (!Flight.isValidDateAndTime24Hours(depart)) {
            throw new IllegalArgumentException("Invalid departure date-time format.");
        }

        // validate dest
        if (!Flight.isValidSrcAndDest(dst)) {
            throw new IllegalArgumentException("Invalid destination airport code.");
        }

        dst = dst.toUpperCase();

        String destination = AirportNames.getName(dst);
        if (destination == null) {
            throw new IllegalArgumentException("The three-letter destination airport code does not correspond to a known airport");
        }

        // validate arrive
        if (!Flight.isValidDateAndTime24Hours(arrive)) {
            throw new IllegalArgumentException("Invalid arrival date-time format.");
        }

        // Covert string to date
        Date arriveDate;
        Date departDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            departDate = simpleDateFormat.parse(depart);
            arriveDate = simpleDateFormat.parse(arrive);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Flight flight = new Flight(flightNum, src, departDate, dst, arriveDate);
        return flight;
    }

    public static List<Airline>  readAllAirlinesFromXML(File file) throws IOException {
        List<Airline> result = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            InputStream inputStream = new FileInputStream(file);
            XmlParser xmlParser = new XmlParser(inputStream);
            result = xmlParser.parseAirlines();
        } catch (Exception e) {
            throw new IOException("Unable To read from file");
        }

        return result;
    }

    public static void  WriteAllAirlinesToXML(File file, List<Airline> airlines) throws IOException {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            OutputStream outputStream = new FileOutputStream(file);
            XmlDumper xmlDumper = new XmlDumper(outputStream);
            xmlDumper.dump(airlines);
        } catch (Exception e) {
            throw new IOException("Unable To read from file");
        }
    }

//    /**
//     * Read and print read me file.
//     */
//    static void printReadMeFile(String[] args) {
//        if (args.length == 1) {
//            try {
//                InputStream readme = Project5.class.getResourceAsStream("README.txt");
//                BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
//                String line = reader.readLine();
//                while (line != null) {
//                    System.out.println(line);
//                    line = reader.readLine();
//                }
//            } catch (Exception e) {
//                System.err.println("Unable to read README.md");
//            }
//        } else {
//            usage("Invalid number of argument present.");
//        }
//    }

    /**
     * Prints usage information for this program and exits
     *
     * @param message An error message to print
     */
    private static void usage(String message) {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("This simple program posts flight details ");
        err.println("to the server.");
        err.println();
        err.println("To list all the flights of a airline");
        err.println("usage: java -jar target/airline-client.jar -host [host] -port [port] -search [airline]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  airline      Airline name");
        err.println();
        err.println("To list all the flights of a airline from a specific source ot destination");
        err.println("usage: java -jar target/airline-client.jar -host [host] -port [port] -search [airline] [source] [destination]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  airline      Airline name");
        err.println("  source       Source airport");
        err.println("  destination  Destination airport");
        err.println();
        err.println("To add a flight to the server");
        err.println("usage: java -jar target/airline-client.jar -host [host] -port [port] [airline] [flightNumber] [source] [departure] [destination] [arrive]");
        err.println("  host          Host of web server");
        err.println("  port          Port of web server");
        err.println("  airline       Airline name");
        err.println("  flightNumber  FlightNumber");
        err.println("  source        Source airport");
        err.println("  departure     Departure time in AM/PM format");
        err.println("  destination   Destination airport");
        err.println("  arrive        Arrival time in AM/PM format");
        err.println();
        throw new InvalidParameterException("Input given is invalid. Check above error.");
    }

    private static void error(String message) {
        PrintStream err = System.err;
        err.println("** " + message);
    }
}