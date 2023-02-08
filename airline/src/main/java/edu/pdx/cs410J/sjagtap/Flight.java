package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractFlight;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Flight extends AbstractFlight implements Comparable {

    private final int flightNumber;
    private final String src;
    private final String dst;
    private final Date depart;
    private final Date arrive;


    public Flight(int flightNumber, String src, Date depart, String dst, Date arrive) {
        this.flightNumber = flightNumber;
        this.src = src;
        this.depart = depart;
        this.dst = dst;
        this.arrive = arrive;
    }

    public Flight() {
        this.flightNumber = 12;
        this.src = "src";
        this.dst = "dst";
        this.depart = null;
        this.arrive = null; //TODO for now initialize with null

    }



    /**
     * Validate flight number for integer.
     *
     * @param num flight number.
     * @return true if valid value is provided. else false.
     */
    @VisibleForTesting
    static boolean isValidFlightNumber(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate Data and Time given in string format (mm/dd/yyyy hh:mm)
     *
     * @param dateAndTime date time in string format.
     * @return true if valid value is provided. else false.
     */
    @VisibleForTesting
    static boolean isValidDateAndTime(String dateAndTime) {
        String[] splitDateTimes = dateAndTime.split(" ");
        if (splitDateTimes.length != 2) {
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
            return false;
        }

        if (splitTime[0].length() < 0 || splitTime[0].length() > 2) {
            return false;
        }

        if (splitTime[1].length() < 0 || splitTime[1].length() > 2) {
            return false;
        }

        try {
            if (Integer.parseInt(splitTime[0]) > 11) {
                return false;
            }

            if (Integer.parseInt(splitTime[1]) > 59) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            dateFormat.parse(dateAndTime);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Validate Data and Time given in string format (mm/dd/yyyy hh:mm)
     *
     * @param dateAndTime date time in string format.
     * @return true if valid value is provided. else false.
     */
    @VisibleForTesting
    static boolean isValidDateAndTimeAndZone(String dateAndTime) {
        String[] splitDateTimes = dateAndTime.split(" ");
        if (splitDateTimes.length != 3) {
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
            return false;
        }

        if (splitTime[0].length() < 0 || splitTime[0].length() > 2) {
            return false;
        }

        if (splitTime[1].length() < 0 || splitTime[1].length() > 2) {
            return false;
        }

        try {
            if (Integer.parseInt(splitTime[0]) > 11) {
                return false;
            }

            if (Integer.parseInt(splitTime[1]) > 59) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        String zone = splitDateTimes[2];

        if(!zone.equals("am") && !zone.equals("pm")){
            return  false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateAndTime);
        } catch (Exception e) {
            return false;
        }

        return true;
    }


    /**
     * Validate source and destination to be of length 3 and all letters.
     *
     * @param str src or destination argument.
     * @return true if valid value is provided. else false.
     */
    @VisibleForTesting
    static boolean isValidSrcAndDest(String str) {
        // check size of 3
        if (str.length() != 3) {
            return false;
        }

        // Check only letter
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int getNumber() {
        return this.flightNumber;
    }


    @Override
    public String getSource() {
//        String source = AirportNames.getName(this.src);
//        return source;
        return this.src;
    }

    @Override
    public String getDepartureString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy hh:mm aa");
        String departDateTime = dateFormat.format(this.depart);
        return departDateTime;
    }

    @Override
    public String getDestination() {
//        String destination = AirportNames.getName(this.dst);
//        return destination;
        return  this.dst;
    }

    @Override
    public String getArrivalString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy hh:mm aa");
        String arrivalDateTime = dateFormat.format(this.arrive);
        return arrivalDateTime;
    }

    //TODO --> Fix this time difference
    long durationOfFlight(){
        Date departTime = this.depart;
        Date arriveTime = this.arrive;
        double differenceInMiliSec = arriveTime.getTime() - departTime.getTime();
        // Covert value from millisecond to minutes.
        double differenceInMinutes = (differenceInMiliSec / 1000) / 60;
        return (long)differenceInMinutes;
    }

    @Override
    public int compareTo(Object o) {
        Flight flight = (Flight)o;
        if(this.src.equals(flight.src)){
            // sort departure time
            if(this.depart.equals(flight.depart)){
                return 0;
            } else if (this.depart.before(flight.depart)) {
                return -1;
            } else {
                return 1;
            }
        } else {
            // sort on source
            return this.src.compareTo(flight.src);
        }
    }


}
