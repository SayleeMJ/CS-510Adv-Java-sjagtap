package edu.pdx.cs410J.sjagtap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void addAirline(View v)
    {
        Intent i = new Intent(getApplicationContext(), AddAirlineActivity.class);
        startActivity(i);
    }
}
