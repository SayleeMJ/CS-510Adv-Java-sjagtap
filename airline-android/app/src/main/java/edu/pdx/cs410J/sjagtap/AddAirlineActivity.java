package edu.pdx.cs410J.sjagtap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddAirlineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_airline);
    }

    public void AddAirlineButtonHandler(View v) {
        String airlineName = ((TextView) findViewById(R.id.etAirlineName)).getText().toString();

        List<Airline> listAirlines;
        File file;
        if (airlineName == null ||
                airlineName.isEmpty() ||
                !Airline.isValidAirlineName(airlineName)) {
            Toast.makeText(AddAirlineActivity.this, "Invalid airline name.", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            File dir = getApplicationContext().getFilesDir();
            file = new File(dir, "FlightDatabase.xml");
            listAirlines = Options.readAllAirlinesFromXML(file);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to read. Malformed database.", Toast.LENGTH_LONG).show();
            System.out.println("Exception: "+e.getMessage());
            return;
        }

        boolean flag = false;
        for (Airline airline : listAirlines) {
            if (airline.getName().equals(airlineName)) {
                flag = true;
                break;
            }
        }

        if (flag) {
            Toast.makeText(this, "Flight already exists.", Toast.LENGTH_LONG).show();
            return;
        } else {
            Airline airline = new Airline(airlineName);
            listAirlines.add(airline);

//          List<Airline> airlines = new ArrayList<>();
//          airlines.add(airline);
            try {
                Options.WriteAllAirlinesToXML(file, listAirlines);
            } catch (Exception e) {
                Toast.makeText(this, "Enable to add. Malformed database.", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(this, "Airline added successfully.", Toast.LENGTH_LONG).show();
        }
    }
}