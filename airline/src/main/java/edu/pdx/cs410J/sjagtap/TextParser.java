package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A skeletal implementation of the <code>TextParser</code> class for Project 2.
 */
public class TextParser implements AirlineParser<Airline> {
    private final Reader reader;

    public TextParser(Reader reader) {
        this.reader = reader;
    }

    @Override
    public Airline parse() throws ParserException {
        try (
                BufferedReader br = new BufferedReader(this.reader)
        ) {

            String airlineName = br.readLine();

            if (airlineName == null) {
                throw new ParserException("Missing airline name");
            }
            Airline airline = new Airline(airlineName);
            String flightNumber = br.readLine();

            while (flightNumber != null) {
                if (flightNumber == null) {
                    throw new ParserException("Missing flight number");
                }
                int flightNum;
                try {
                    flightNum = Integer.parseInt(flightNumber);
                } catch (Exception e) {
                    throw new ParserException("Invalid Flight Number");
                }


                String src = br.readLine();
                if (src == null) {
                    throw new ParserException("Missing src name");
                }
                //add validation code from project 1
                String depart = br.readLine();
                if (depart == null) {
                    throw new ParserException("Missing depart name");
                }

                String dst = br.readLine();
                if (dst == null) {
                    throw new ParserException("Missing dst name");
                }

                String arrive = br.readLine();
                if (arrive == null) {
                    throw new ParserException("Missing arrive name");
                }

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
                airline.addFlight(flight);
                flightNumber = br.readLine();
            }
            return airline;
        } catch (IOException e) {
            throw new ParserException("While parsing airline text", e);
        }
    }



    public Airline prettyParse() throws ParserException {
        try (
                BufferedReader br = new BufferedReader(this.reader)
        ) {

            String airlineName = br.readLine();

            if (airlineName == null) {
                throw new ParserException("Missing airline name");
            }
            Airline airline = new Airline(airlineName);
            String flightNumber = br.readLine();

            while (flightNumber != null) {
                if (flightNumber == null) {
                    throw new ParserException("Missing flight number");
                }
                int flightNum;
                try {
                    flightNum = Integer.parseInt(flightNumber);
                } catch (Exception e) {
                    throw new ParserException("Invalid Flight Number");
                }


                String src = br.readLine();
                if (src == null) {
                    throw new ParserException("Missing src name");
                }
                //add validation code from project 1
                String depart = br.readLine();
                if (depart == null) {
                    throw new ParserException("Missing depart name");
                }

                String dst = br.readLine();
                if (dst == null) {
                    throw new ParserException("Missing dst name");
                }

                String arrive = br.readLine();
                if (arrive == null) {
                    throw new ParserException("Missing arrive name");
                }

                String duration  = br.readLine();

                Date arriveDate;
                Date departDate;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
                try {
                    departDate = simpleDateFormat.parse(depart);
                    arriveDate = simpleDateFormat.parse(arrive);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Flight flight = new Flight(flightNum, src, departDate, dst, arriveDate);
                airline.addFlight(flight);
                flightNumber = br.readLine();
            }
            return airline;
        } catch (IOException e) {
            throw new ParserException("While parsing airline text", e);
        }
    }
}
