package edu.pdx.cs410J.sjagtap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class SearchAirline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_airline);
    }

    public void onSearch(View v) {
        String airlineName = ((TextView) findViewById(R.id.airlineSearchInput)).getText().toString();
        String source = ((TextView) findViewById(R.id.sourceSearchInput)).getText().toString();
        String destination = ((TextView) findViewById(R.id.destSearchInput)).getText().toString();
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = getApplicationContext().openFileInput( "inputFile.xml");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch(Exception e)
        {
            Log.println(Log.ERROR,"test", e.getMessage());
        }


        Intent i = new Intent(getApplicationContext(), PrettyPrintActivity.class);
        i.putExtra("printResult", sb.toString());
        startActivity(i);
//        TextView view = findViewById(R.id.searchOutput);
//        // read from airline and search
//        view.setText("Results blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blahResults blah blah");
//    }
    }
}