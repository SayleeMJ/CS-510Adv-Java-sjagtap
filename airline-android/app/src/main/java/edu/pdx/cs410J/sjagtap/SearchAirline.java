package edu.pdx.cs410J.sjagtap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SearchAirline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_airline);
    }

    public void onSearch(View v) {
        String airlineName = ((TextView) findViewById(R.id.etAirlineName)).getText().toString();
        String source = ((TextView) findViewById(R.id.etSourceAirport)).getText().toString();
        String destination = ((TextView) findViewById(R.id.etDestinationAirport)).getText().toString();

        if (airlineName == null || airlineName.isEmpty()) {
            Toast.makeText(this, "Airline name is not optional.", Toast.LENGTH_LONG).show();
            return;
        }

        Intent i = new Intent(getApplicationContext(), PrettyPrintActivity.class);
        i.putExtra("Operation", "Search");
        i.putExtra("airlineName", airlineName);
        i.putExtra("source", source);
        i.putExtra("destination", destination);

        startActivity(i);
    }
}