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
     * Main entry point of program.
     *
     * @param args list of command line arguments.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Missing command line arguments" + Options.getHelpMessage());
            return;
        }

        String option = args[0];
        switch (option) {
            case "-README":
                Options.printReadMeFile(args);
                break;
            case "-print":
                Options.printUsingCMDLine(args);
                break;
            default:
                System.err.println("Invalid option");
        }
    }
}
