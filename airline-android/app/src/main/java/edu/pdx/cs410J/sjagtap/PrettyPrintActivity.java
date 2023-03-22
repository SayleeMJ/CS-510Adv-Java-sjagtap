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
        return "<p align=\"justify\">"+ "This is a README file!" + "<br>" +
                "Name: Saylee Mohan Jagtap" + "<br>" +
                "Odin ID: sjagtap" + "<br>" +
                "PSU ID: 972267084"+ "<br><br>" +
                "This android application creates an Airline object with Airline name." + "<br><br>"+
                "This android application has functionalities:"+ "<br>"+
                "1. To add an Airline only with Airline name. " +"<br>"+
                "2. To add Airline with all the flights for this airline." +"<br>"+
                "3. Each flight object has flight number, source, depart date time, destination, arrive date time." + "<br>"+
                "4. After creating Airline it reads/writes the object's data into the xml file which is used as storage source." + "<br>"+
                "5. To search flights for a specified airline or specified source and destination along with airline name." +"<br>"+
                "6. To pretty print all the airlines with their corresponding flights." +"<br>"+
                "7. Help option that guides user to access application functionalities"+ "<br><br>"+
                "I have used system android gestures functionality to move bach to earlier screen."+ "<br>"+
                "To use this functionality you can change the setting."+"<br>" +
                "To do this <b>Go to setting > System > Locate 3 Button Navigation Switch </b></p>";
    }

    private String getHelp() {
        return "<b>What is Airline app?</b>" + "<br>" +
                "<p align=\"justify\">The Airline is an application that supports features such as add new airline and new flight details. Search airline with specified name along with specified source and destination name and many more features.</p>" +
                "<br>" +
                "<b>How do I use the app?</b>" + "<br>" +
                "<p align=\"justify\">We suggest starting by adding new airline. To do this, tap on the Add Airline button on the menu screen for add new airline name and Add flight details by tapping on the Add Flight button on the menu screen.</p>" +
                "<br>" +
                "<b>How do I use other features?</b>" +"<br>" +"<br>" +
                "<b>Search: </b>" +"<br>" +
                "<p align=\"justify\">This feature will provide you the options for searching specified airline by only airline or along with specified source or destination airport name.</p>" +
                "<p align=\"justify\">To do this tap on the Search button and provide the details as per requirements.</b>" + "<br>" + "<br>" +
                "<b>Pretty Print:</b>" +
                "<p align=\"justify\">This feature will provide you all the airline details in the pretty format. To do this tap on Pretty Print button on the menu screen.</p>" +
                "<b>ReadMe: </b>" +
                "<p align=\"justify\">This feature will provide you details of about the owner of this application . To do this tap on ReadMe button on the menu screen.</p>";
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

        sb.append("<p><b>Flights for Airline " + searchedAirline.getName() + "</b></p>");


        int searchIndex = 0;
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

            sb.append("<p>"+ (searchIndex + 1) + ". "  + flight.ToStringPretty() + "</p>");
            searchIndex++;
        }

        // Print.
        return sb.toString();
    }

    private String PrintPrettyOption(List<Airline> listAirLine) {
        StringBuilder sb = new StringBuilder();
        for (Airline airline : listAirLine) {
            sb.append("<p><b>Flights for Airline " + airline.getName() + "</b></p>");
            List<Flight> list = new ArrayList<Flight>(airline.getFlights());
            for (int index = 0; index < list.size(); index++) {
                sb.append("<p>" + (index + 1) + ". " + list.get(index).ToStringPretty() + "</p>");
            }
            sb.append("<p><b>-------------------------------------------------------------------</b></p>");
        }

        return sb.toString();
    }
}