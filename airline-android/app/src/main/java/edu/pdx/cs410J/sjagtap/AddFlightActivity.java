package edu.pdx.cs410J.sjagtap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class AddFlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);
    }

    public void addAirline(View v)
    {
        String airlineName = ((TextView) findViewById(R.id.etAirlineName)).getText().toString();
        String flightNumber = ((TextView) findViewById(R.id.etFLightNumber)).getText().toString();
        String source = ((TextView) findViewById(R.id.etSource)).getText().toString();
        String departureDate = ((TextView) findViewById(R.id.etDeparture)).getText().toString();
        String destination = ((TextView) findViewById(R.id.etDestination)).getText().toString();
        String arrivalDate = ((TextView) findViewById(R.id.etArrival)).getText().toString();

        Flight newFlight = null;
        try{
            newFlight = Options.createAndValidateFlightForPretty(flightNumber, source, departureDate, destination, arrivalDate);

        } catch (IllegalArgumentException illegalArgumentException){
            Toast.makeText(this, illegalArgumentException.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (Exception exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        try{
            File dir = getApplicationContext().getFilesDir();
            File file = new File(dir, "FlightDatabase.xml");
            Options.readAndWriteToXmlFile(file, airlineName, newFlight);
        } catch (IllegalArgumentException illegalArgumentException){
            Toast.makeText(this, illegalArgumentException.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (Exception exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

    }

//    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody) {
//        File dir = mcoContext.getFilesDir();
//        try {
//            File gpxfile = new File(dir, sFileName);
//            FileWriter writer = new FileWriter(gpxfile);
//            writer.append(sBody);
//            writer.flush();
//            writer.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}