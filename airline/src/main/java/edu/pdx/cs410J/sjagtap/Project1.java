package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
  }

  public static void main(String[] args) {
    System.err.println("Missing command line arguments");
    //
    for (String arg : args) {
      System.out.println(arg);
      System.out.println("abc");
    }
  }

}