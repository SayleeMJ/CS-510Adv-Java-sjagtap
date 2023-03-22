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
            throw new IllegalArgumentException("The three-letter source airport code does not correspond to a known airport");
        }

        // validate depart. chekc this
        if (!Flight.isValidDateAndTimeAndZone12Hour(depart)) {
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

        // validate arrive. check this
        if (!Flight.isValidDateAndTimeAndZone12Hour(arrive)) {
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
            throw new IllegalArgumentException("Invalid date-time format.");
        }

        if (arriveDate.before(departDate)) {
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

    public static List<Airline> readAllAirlinesFromXML(File file) throws IOException {
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

    public static void WriteAllAirlinesToXML(File file, List<Airline> airlines) throws IOException {
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

}