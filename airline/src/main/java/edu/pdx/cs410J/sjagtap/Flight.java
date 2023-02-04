package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractFlight;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Flight extends AbstractFlight {

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
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
        return this.src;
    }

    @Override
    public String getDepartureString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy hh:mm a");
        String departDateTime = dateFormat.format(depart);
        return departDateTime;
    }

    @Override
    public String getDestination() {
        return this.dst;
    }

    @Override
    public String getArrivalString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy hh:mm a");
        String arrivalDateTime = dateFormat.format(arrive);
        return arrivalDateTime;
    }
}
