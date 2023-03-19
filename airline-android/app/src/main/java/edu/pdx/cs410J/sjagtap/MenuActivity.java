package edu.pdx.cs410J.sjagtap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void addFlight(View v)
    {
        Intent i = new Intent(getApplicationContext(), AddFlightActivity.class);
        startActivity(i);
    }

    public void addAirline(View v)
    {
        Intent i = new Intent(getApplicationContext(), AddAirlineActivity.class);
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
        i.putExtra("Operation", "PrintAll");
        startActivity(i);
    }

    public void readMe(View v)
    {
        Intent i = new Intent(getApplicationContext(), PrettyPrintActivity.class);
        i.putExtra("Operation", "ReadMe");
        startActivity(i);
    }

    public void help(View v)
    {
        Intent i = new Intent(getApplicationContext(), PrettyPrintActivity.class);
        i.putExtra("Operation", "Help");
        startActivity(i);
    }

}
