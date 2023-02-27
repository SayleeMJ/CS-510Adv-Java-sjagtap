package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

    public static void main(String... args) {
        String hostName = null;
        String portString = null;
        String airlineName = null;
        String flightNumber = null;
        String src = null;
        String dest = null;
        String depart = null;
        String arrive = null;
        String option = null;
        String arriveTime = null;
        String departTime = null;
        String arriveAMPM = null;
        String departAMPM = null;

        int i = 0;
        int argsLength = args.length;
        while(i < args.length)
        {
            if (hostName == null && args[i].equals("-host") && i+1 < argsLength)
            {
                hostName = args[i+1];
                i += 1;
            }
            else if (hostName != null && args[i].equals("-host"))
            {
                usage("Extraneous command line argument: -host ");
            }
            else if (portString == null && args[i].equals("-port") && i+1 < argsLength)
            {
                portString = args[i+1];
                i += 1;
            }
            else if (portString != null && args[i].equals("-port"))
            {
                usage("Extraneous command line argument: -port ");
            }
            else if (args[i].equals("-search"))
            {
                option = "search";
                int k = i+1;
                while(k < argsLength) {
                    if (airlineName == null)
                    {
                        airlineName = args[k];
                    }
                    else if (src == null)
                    {
                        src = args[k];
                    }
                    else if (dest == null)
                    {
                        dest = args[k];
                    }
                    else {
                        usage("Extraneous command line argument: " + args[k]);
                    }
                    k += 1;
                }
                i = k;
            }
            else
            {
                if (i < argsLength) {
                    airlineName = args[i];
                    i += 1;
                }

                int j = i;
                while (j < argsLength){
                    option = "post";
                    if (flightNumber == null)
                    {
                        flightNumber = args[j];
                    }
                    else if (src == null)
                    {
                        src = args[j];
                    }
                    else if (depart == null)
                    {
                        depart = args[j];
                    }
                    else if (departTime == null)
                    {
                        departTime = args[j];
                    }
                    else if (departAMPM == null)
                    {
                        departAMPM = args[j];
                    }
                    else if (dest == null)
                    {
                        dest = args[j];
                    }
                    else if (arrive == null)
                    {
                        arrive = args[j];
                    }
                    else if (arriveTime == null)
                    {
                        arriveTime = args[j];
                    }
                    else if (arriveAMPM == null)
                    {
                        arriveAMPM = args[j];
                    }
                    else {
                        usage("Extraneous command line argument: " + args[i]);
                    }

                    j+=1;

                }

                i = j;
            }

            i += 1;
        }

        if (hostName == null) {
            usage( "Missing hostname" );
            return;

        } else if ( portString == null) {
            usage( "Missing port" );
            return;
        } else if ( airlineName == null)
        {
            usage( "Missing airline name");
            return;
        } else if ( option != null && option.equals("search"))
        {
            if (src == null)
            {
                usage( "Missing source");
            }
            else if (dest == null)
            {
                usage( "Missing destination" );
            }
        } else if (option != null && option.equals("post"))
        {
            if (flightNumber == null)
            {
                usage( "Missing flightNumber");
            } else if (src == null)
            {
                usage( "Missing source" );
            } else if (dest == null)
            {
                usage ("Missing destination");
            } else if (depart == null)
            {
                usage ("Missing departure");
            } else if (arrive == null)
            {
                usage ("Missing arrival");
            }
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        // flightNumber
         if (flightNumber != null) {
             int flightNo;
             try {
                 flightNo = Integer.parseInt(flightNumber);
             } catch (NumberFormatException ex) {
                 usage("flightNumber \"" + flightNumber + "\" must be an integer");
                 return;
             }
         }

         arrive = arrive + " " + arriveTime + " " + arriveAMPM;
         if (arrive != null) {
             // arrive and depart
             Date arriveDate;
             try {
                 arriveDate = new SimpleDateFormat("MM/dd/yyyy HH:mm a").parse(arrive);
             } catch (ParseException e) {
                 usage("arrive\"" + arrive + "\" must be in MM/dd/yy HH:mm AM/PM format");
                 return;
             }
         }

         depart = depart + " " + departTime + " " + departAMPM;
         if (depart != null) {
             Date departDate;
             try {
                 departDate = new SimpleDateFormat("MM/dd/yyyy HH:mm a").parse(depart);
             } catch (ParseException e) {
                 usage("depart\"" + depart + "\" must be in MM/dd/yy HH:mm AM/PM format");
                 return;
             }
         }

        AirlineRestClient client = new AirlineRestClient(hostName, port);

        String message;
        try {
//            if (airlineName == null) {
//                List<Airline> airlineList = client.getAllDictionaryEntries();
//                StringWriter sw = new StringWriter();
//                PrettyPrinter pretty = new PrettyPrinter(sw);
//                for(Airline airline : airlineList) {
//                    pretty.dump(airline);
//                }
//                message = sw.toString();
//            } else
            if (airlineName != null && src == null && dest == null)
            {
                Airline airline = client.getFlights(airlineName);
                Writer sw = new StringWriter();
                PrettyPrinter pretty = new PrettyPrinter(sw);
                pretty.dump(airline);
                message = sw.toString();
            } else if (airlineName != null && src != null && dest != null && option.equals("search"))
            {
                Airline airline = client.getFlightsFromSrcDestination(airlineName, src, dest);
                StringWriter sw = new StringWriter();
                PrettyPrinter pretty = new PrettyPrinter(sw);
                pretty.dump(airline);
                message = sw.toString();
            } else
            {
                // Post the airline
                client.addFlightEntry(airlineName, flightNumber, src, depart, dest, arrive);
                message = Messages.definedAirlineAs(airlineName, flightNumber);
            }

        } catch (IOException | ParserException ex ) {
            error("While contacting server: " + ex.getMessage());
            return;
        } catch (HttpRequestHelper.RestException exception) {
            error(exception.getHttpStatusCode() + exception.getMessage() );
            return;
        }

        System.out.println(message);
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("This simple program posts flight details ");
        err.println("to the server.");
        err.println();
        err.println("To list all the flights of a airline");
        err.println("usage: java -jar target/airline-client.jar -host [host] -port [port] [airline]");
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
    }
}