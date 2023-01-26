package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;

import java.text.SimpleDateFormat;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

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

      if (Integer.parseInt(splitTime[1]) > 24) {
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
  @VisibleForTesting
  static boolean isValidAirlineName(String str) {
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isLetterOrDigit(str.charAt(i))) {
        return false;
      }
    }
    return true;
  }
  public static void main(String[] args) {
    if (args.length != 9) {
      System.err.println("Missing command line arguments" + getHelpMessage());
      return;
    }

    // validate option
    if (isValidOption(args[0])) {
      System.err.println("Invalid option");
      return;
    }

    // validate flight number
    String airline = args[1];
    if(isValidAirlineName(airline)){
      System.err.println("Invalid Airline Name");
      return;
    }
    // Validate flight number
    String flightNumber = args[2];
    if(isValidFlightNumber(flightNumber)){
      System.err.println("Invalid flight number");
      return;
    }

    // validate src
    String src = args[3];
    if (!isValidSrcAndDest(src)) {
      System.err.println("Invalid src");
      return;
    }

    // validate depart
    String date = args[4] + " " + args[5];
    if (!isValidDateAndTime(date)) {
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
  }

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

  @VisibleForTesting
  static boolean isValidOption(String option) {
    if (option.equals("-print") || option.equals("-README")) {
      return true;
    }

    return false;
  }

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