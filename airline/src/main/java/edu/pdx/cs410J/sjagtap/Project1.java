package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;

import java.text.SimpleDateFormat;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    SimpleDateFormat date = new SimpleDateFormat("mm/dd/yyyy hh:mm");
    try {
      date.parse(dateAndTime);
    } catch (Exception e) {
      return false;
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

    // Validate flight number
    String flightNumber = args[2];
    int flightNum;
    try {
      flightNum = Integer.parseInt(flightNumber);
    } catch (Exception e) {
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
    if (option != "-print" || option != "-README") {
      return false;
    }

    return true;
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

}