package edu.pdx.cs410J.sjagtap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddAirlineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_airline);
    }

    public void addAirline(View v)
    {
        writeFileOnInternalStorage(getApplicationContext(), "inputFile.xml","test");
        String airlineName = ((TextView) findViewById(R.id.airlineName)).getText().toString();
        TextView flightNumberView = findViewById(R.id.flightNumberInput);
        int flightNumber = Integer.parseInt(flightNumberView.getText().toString());
        String src = ((TextView) findViewById(R.id.sourceInput)).getText().toString();
        String dest = ((TextView) findViewById(R.id.dstInput)).getText().toString();
        String arriveDateString = ((TextView) findViewById(R.id.arriveDateInput)).getText().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String departDateString = ((TextView) findViewById(R.id.departDateInput)).getText().toString();
        Date arriveDate;
        Date departDate;
        try {
            departDate = simpleDateFormat.parse(arriveDateString);
            arriveDate = simpleDateFormat.parse(departDateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Airline airline = new Airline(airlineName);
        Flight flight = new Flight(flightNumber, src, departDate, dest, arriveDate);
        airline.addFlight(flight);



    }

    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody){
        File dir = mcoContext.getFilesDir();
//        if(!dir.exists()){
//            dir.mkdir();
//        }

        try {
            File gpxfile = new File(dir, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}