package edu.pdx.cs410J.sjagtap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void addAirline(View v)
    {
        Intent i = new Intent(getApplicationContext(), AddFlightActivity.class);
        startActivity(i);
    }

    public void searchAirline(View v)
    {
        Intent i = new Intent(getApplicationContext(), SearchAirline.class);
        startActivity(i);
    }

    public void prettyPrint(View v)
    {
        Intent i = new Intent(getApplicationContext(), PrettyPrintActivity.class);
        startActivity(i);
    }

    public void readMe(View v)
    {
        Intent i = new Intent(getApplicationContext(), PrettyPrintActivity.class);
        startActivity(i);
    }

}