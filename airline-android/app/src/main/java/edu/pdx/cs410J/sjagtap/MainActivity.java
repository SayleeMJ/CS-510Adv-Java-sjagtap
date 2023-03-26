package edu.pdx.cs410J.sjagtap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = (Button) findViewById(R.id.btAddFlight);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                File file;
                FileWriter writer;
                File dir = getApplicationContext().getFilesDir();
                file = new File(dir, "FlightDatabase.xml");
                if(!file.exists()){
                    try {
                        file.createNewFile();
                        writer = new FileWriter(file);
                        writer.write("<?xml version=\"1.0\" encoding=\"us-ascii\"?>\n" +
                                "<airlines>\n" +
                                "\n" +
                                "</airlines>");
                        writer.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(i);
            }
        });
    }
}