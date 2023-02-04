package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Project3 {

    /**
     * Main entry point of program.
     *
     * @param args list of command line arguments.
     */
    public static void main(String[] args) {

        // Validate length of args
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
                Options.printUsingCommandLine(args);
                break;
            case "-textFile":
                Options.readAndWrite(args);
                break;
            default:
                System.err.println("Invalid option");
        }
    }


}
