package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.ParserException;

import java.io.*;

public class Converter {

    /**
     * Main entry point of program for converter class.
     *
     * @param args list of command line arguments.
     */
    public static void main(String[] args) {
        String text = args[0];
        String xmlFileName = args[1];
        File textFile = new File(text);
        File xmlFile = new File(xmlFileName);
        converter(textFile, xmlFile);

    }

    /**
     * Reads the text file and converts it into xml file.
     *
     * @param textFile contains path of text file which needs to be converted.
     * @param xmlFile contains path of xml file
     */
    public static void converter(File textFile, File xmlFile) {

        Airline airline = null;
        if (textFile.exists())
            try {
                // Read airline info.
                Reader r = new FileReader(textFile);
                TextParser textParser = new TextParser(r);
                airline = textParser.parse();

            } catch (ParserException | IOException e) {
                System.err.println("Text file is empty. Please add flight details first. " + e.getMessage());
                return;
            }
        else {
            System.err.println("Please provide correct and existing file name");
            return;
        }

        // write to xml file

        if (xmlFile.exists()) {
            try {
                OutputStream outputStream = new FileOutputStream(xmlFile);
                XmlDumper xmlDumper = new XmlDumper(outputStream);
                xmlDumper.dump(airline);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                xmlFile.createNewFile();
                OutputStream outputStream = new FileOutputStream(xmlFile);
                XmlDumper xmlDumper = new XmlDumper(outputStream);
                xmlDumper.dump(airline);

            } catch (IOException e) {
                System.err.println("Enable to create file since its not present.");
            }

        }
    }

}
