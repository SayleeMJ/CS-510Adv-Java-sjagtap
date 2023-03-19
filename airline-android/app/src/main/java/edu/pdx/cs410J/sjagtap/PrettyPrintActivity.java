package edu.pdx.cs410J.sjagtap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PrettyPrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretty_print);
        Intent i = getIntent();
        String operation = i.getStringExtra("Operation");
        String contentToDisplay = "";

        if (operation == null) {
            Log.i("AirlineApplication", "Operation is null");
            return;
        }

        if (operation.equals("PrintAll")) {
            List<Airline> listAirLine = new ArrayList<>();
            try {
                File dir = getApplicationContext().getFilesDir();
                File file = new File(dir, "FlightDatabase.xml");
                listAirLine = Options.readAllAirlinesFromXML(file);
            } catch (Exception e) {
                Toast.makeText(this, "Unable to read. Malformed database.", Toast.LENGTH_LONG).show();
            }

            contentToDisplay = PrintPrettyOption(listAirLine);
        } else if (operation.equals("Search")) {
            String airlineName = i.getStringExtra("airlineName");
            String source = i.getStringExtra("source");
            String destination = i.getStringExtra("destination");
            List<Airline> listAirLine = new ArrayList<>();

            try {
                File dir = getApplicationContext().getFilesDir();
                File file = new File(dir, "FlightDatabase.xml");
                listAirLine = Options.readAllAirlinesFromXML(file);
            } catch (Exception e) {
                Toast.makeText(this, "Unable to read. Malformed database.", Toast.LENGTH_LONG).show();
            }

            contentToDisplay = Search(airlineName, source, destination, listAirLine);
        } else if (operation.equals("Help")) {
            contentToDisplay = getHelp();
        } else if (operation.equals("ReadMe")) {
            contentToDisplay = getReadMe();
        }

        TextView textView = findViewById(R.id.tvCommonText);
        textView.setText(Html.fromHtml(contentToDisplay));
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    private String getReadMe() {
        return "Read me " +
                "here";
    }

    private String getHelp() {
        return "Help Text" +
                "Here";
    }

    private String Search(String airlineName, String source, String destination, List<Airline> listAirLine) {

        StringBuilder sb = new StringBuilder();
        // Find Airline.
        Airline searchedAirline = null;
        for (Airline airline : listAirLine) {
            if (airline.getName().equals(airlineName)) {
                searchedAirline = airline;
                break;
            }
        }

        if (searchedAirline == null) {
            sb.append("Airline is not present in database.");
            return sb.toString();
        }

        sb.append("<p><b>Airline Name : " + searchedAirline.getName() + "</b></p>");

        // Filter airlines.
        for (Flight flight : searchedAirline.getFlights()) {
            if (source != null && !source.isEmpty() && !source.equals(flight.getSource())) {
                // if source is present and source is not equal to flight source. Do not add.
                continue;
            }

            if (destination != null && !destination.isEmpty() && !destination.equals(flight.getDestination())) {
                // if destination is present and destination is not equal to flight destination. Do not add.
                continue;
            }

            sb.append("<p>" + flight.ToStringPretty() + "</p>");
        }

        // Print.
        return sb.toString();
    }

    private String PrintPrettyOption(List<Airline> listAirLine) {
        StringBuilder sb = new StringBuilder();
        for (Airline airline : listAirLine) {
            sb.append("<p><b>Airline Name : " + airline.getName() + "</b></p>");
            List<Flight> list = new ArrayList<Flight>(airline.getFlights());
            for (int index = 0; index < list.size(); index++) {
                sb.append("<p>" + (index + 1) + ". " + list.get(index).ToStringPretty() + "</p>");
            }
        }

        return sb.toString();
    }
}