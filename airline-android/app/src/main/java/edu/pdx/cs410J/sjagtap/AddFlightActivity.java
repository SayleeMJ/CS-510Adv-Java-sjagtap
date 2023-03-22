package edu.pdx.cs410J.sjagtap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class AddFlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);
    }

    public void AddFlightButtonHandler(View v) {
        String airlineName = ((TextView) findViewById(R.id.etAirlineName)).getText().toString();
        String flightNumber = ((TextView) findViewById(R.id.etFLightNumber)).getText().toString();
        String source = ((TextView) findViewById(R.id.etSource)).getText().toString();
        String departureDate = ((TextView) findViewById(R.id.etDeparture)).getText().toString();
        String destination = ((TextView) findViewById(R.id.etDestination)).getText().toString();
        String arrivalDate = ((TextView) findViewById(R.id.etArrival)).getText().toString();

        Flight newFlight = null;
        try {
            newFlight = Options.createAndValidateFlightForPretty(flightNumber, source, departureDate, destination, arrivalDate);

        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, illegalArgumentException.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (Exception exception) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        try {
            File dir = getApplicationContext().getFilesDir();
            File file = new File(dir, "FlightDatabase.xml");
            List<Airline> listAirlines = Options.readAllAirlinesFromXML(file);

            boolean flag = false;
            for (Airline airline : listAirlines) {
                if (airline.getName().equals(airlineName)) {
                    airline.addFlight(newFlight);
                    flag = true;
                    break;
                }
            }

            if (flag) {
                Options.WriteAllAirlinesToXML(file, listAirlines);
                Toast.makeText(this, "Flight added successfully.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Airline not found.", Toast.LENGTH_LONG).show();
            }

        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, illegalArgumentException.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (Exception exception) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
    }
}