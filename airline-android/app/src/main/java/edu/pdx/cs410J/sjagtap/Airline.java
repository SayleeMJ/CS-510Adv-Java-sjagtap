package edu.pdx.cs410J.sjagtap;

import com.google.common.annotations.VisibleForTesting;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirportNames;

import java.util.*;

/**
 * Class for airline information.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
    private final String name;
    ArrayList<Flight> arrayList;

    /**
     * Constructor for airline.
     *
     * @param name airline name.
     */
    public Airline(String name) {
        this.name = name;
        arrayList = new ArrayList<Flight>();
    }

    /**
     * Get airline name.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Add flight to airline.
     */
    @Override
    public void addFlight(Flight flight) {
        arrayList.add(flight);
    }

    /**
     * Get list of flights in sorted order first by src then by departure time.
     */
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

