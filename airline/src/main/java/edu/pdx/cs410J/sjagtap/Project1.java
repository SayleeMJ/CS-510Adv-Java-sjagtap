package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;

import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  /**
   * Validate Data and Time given in string format (mm/dd/yyyy hh:mm)
   *
   * @param  dateAndTime  date time in string format.
   * @return      true if valid value is provided. else false.
   */
  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    String[] splitDateTimes = dateAndTime.split(" ");
    if(splitDateTimes.length != 2){
      return false;
    }

    // Validate date
    String date = splitDateTimes[0];
    String[] splitDates = date.split("/");
    if (splitDates.length != 3) {
      return false;
    }

    if (splitDates[0].length() > 2 || splitDates[0].length() < 0) {
      return false;
    }

    if (splitDates[1].length() > 2 || splitDates[1].length() < 0) {
      return false;
    }

    if (splitDates[2].length() > 4 || splitDates[2].length() < 0) {
      return false;
    }

    String time = splitDateTimes[1];

    String[] splitTime = time.split(":");

    if (splitTime.length != 2) {
      return  false;
    }

    if (splitTime[0].length() < 0 || splitTime[0].length() > 2) {
      return false;
    }

    if (splitTime[1].length() < 0 || splitTime[1].length() > 2) {
      return false;
    }

    try {
      if (Integer.parseInt(splitTime[0]) > 24) {
        return false;
      }

      if (Integer.parseInt(splitTime[1]) > 59) {
        return false;
      }

    }
    catch (Exception e) {
      return false;
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy hh:mm");
    try {
      dateFormat.parse(dateAndTime);
    } catch (Exception e) {
      return false;
    }

    return true;
}

  /**
   * Validate airline name to be alphanumeric.
   *
   * @param  str  airline name.
   * @return      true if valid value is provided. else false.
   */
  @VisibleForTesting
  static boolean isValidAirlineName(String str) {
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isLetterOrDigit(str.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Read and print read me file.
   */
  static void printReadMeFile() {
    try {
      InputStream readme = Project1.class.getResourceAsStream("README.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      while(line!= null)
      {
        System.out.println(line);
        line = reader.readLine();
      }
    }
    catch (Exception e){
      System.err.println("Unable to read README.md");
    }
  }

  /**
   * Main entry point of program.
   *
   * @param  args  list of command line arguments.
   */
  public static void main(String[] args) {
    if (args.length == 1 && args[0].equals("-README")) {
      printReadMeFile();
      return;
    }
    if (args.length != 9) {
      System.err.println("Missing command line arguments" + getHelpMessage());
      return;
    }

    // validate option
    if (!isValidOption(args[0])) {
      System.err.println("Invalid option");
      return;
    }

    // validate flight number
    String airline = args[1];
    if(!isValidAirlineName(airline)){
      System.err.println("Invalid Airline Name");
      return;
    }

    // Validate flight number
    String flightNumber = args[2];
    if(!isValidFlightNumber(flightNumber)){
      System.err.println("Invalid flight number");
      return;
    }

    int flightNum = Integer.parseInt(flightNumber);

    // validate src
    String src = args[3];
    if (!isValidSrcAndDest(src)) {
      System.err.println("Invalid src");
      return;
    }

    // validate depart
    String depart = args[4] + " " + args[5];
    if (!isValidDateAndTime(depart)) {
      System.err.println("Invalid depart date");
      return;
    }

    // validate dest
    String dst = args[6];
    if (!isValidSrcAndDest(dst)) {
      System.err.println("Invalid destination code");
      return;
    }

    // validate arrive
    String arrive = args[7] + " " + args[8];
    if (!isValidDateAndTime(arrive)) {
      System.err.println("Invalid destination code");
      return;
    }

    Flight flight = new Flight(flightNum,src, depart, dst, arrive);
    Airline airline1 = new Airline(airline);
    airline1.addFlight(flight);

    for(Flight f : airline1.getFlights()) {
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
   * Validate option argument passed to be either print or README.
   *
   * @param  option  provided option.
   * @return      true if valid value is provided. else false.
   */
  @VisibleForTesting
  static boolean isValidOption(String option) {
    if (option.equals("-print")) {
      return true;
    }

    return false;
  }

  /**
   * Validate source and destination to be of length 3 and all letters.
   *
   * @param  str  src or destination argument.
   * @return      true if valid value is provided. else false.
   */
  @VisibleForTesting
  static boolean isValidSrcAndDest(String str) {
    // check size of 3
    if (str.length() != 3) {
      return false;
    }

    // Check only letter
    for (int i = 0; i < str.length(); i++) {
      if(!Character.isLetter(str.charAt(i))) {
        return false;
      }
    }

    return true;
  }

  @VisibleForTesting
  static boolean isValidFlightNumber(String num){
    try {
        Integer.parseInt(num);
        return true;
    } catch (Exception e) {
      return false;
    }
  }
}