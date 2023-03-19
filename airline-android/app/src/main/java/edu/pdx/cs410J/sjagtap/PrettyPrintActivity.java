package edu.pdx.cs410J.sjagtap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PrettyPrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretty_print);
        Intent i = getIntent();
        String operation = i.getStringExtra("Operation");

        if (operation.equals("PrintAll")) {
            PrintPrettyOption();
        }
    }

    private void PrintPrettyOption() {
        StringBuilder sb = new StringBuilder();
        TextView textView = findViewById(R.id.tvCommonText);

        List<Airline> listAirLine = new ArrayList<>();

        try {
            File dir = getApplicationContext().getFilesDir();
            File file = new File(dir, "FlightDatabase.xml");
            listAirLine = Options.readAllAirlinesFromXML(file);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to read. Malformed database.", Toast.LENGTH_LONG).show();
        }

        for (Airline airline: listAirLine) {
            sb.append("<p><b>Airline Name : " + airline.getName() + "</b></p>");
            List<Flight> list =  new ArrayList<Flight>(airline.getFlights());
            for (int index  = 0; index < list.size(); index++) {
                sb.append("<p>" + (index + 1) +  ". " + list.get(index).ToStringPretty() + "</p>");
            }
        }

        textView.setText(Html.fromHtml(sb.toString()));
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
}