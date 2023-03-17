package edu.pdx.cs410J.sjagtap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ScrollView;

public class PrettyPrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretty_print);
        Intent i = getIntent();
        String message = i.getStringExtra("printResult");
        EditText v = findViewById(R.id.prettyPrintOutput);
        v.setText(message);
    }
}