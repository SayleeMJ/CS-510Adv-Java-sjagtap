package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirportNames;

import java.util.*;

public class Airline extends AbstractAirline<Flight> {
    private final String name;
    ArrayList<Flight> arrayList;

    public Airline(String name) {
        this.name = name;
        arrayList = new ArrayList<Flight>();
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addFlight(Flight flight) {
        arrayList.add(flight);
    }

    @Override
    public Collection<Flight> getFlights() {
        Flight[] flightArray = arrayList.toArray(new Flight[0]);
        Arrays.sort(flightArray);
        List covertedArrayList = Arrays.asList(flightArray);
        return covertedArrayList;
    }


    /**
     * Validate airline name to be alphanumeric.
     *
     * @param str airline name.
     * @return true if valid value is provided. else false.
     */
    @VisibleForTesting
    static boolean isValidAirlineName(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
