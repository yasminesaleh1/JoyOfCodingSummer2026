package edu.pdx.cs.joy.ysaleh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.background), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void createAirline(View view) {
        Intent intent = new Intent(this, newAirlineActivity.class);
        startActivity(intent);

        // use for errors?
        //Toast.makeText(this, "Error: ...", Toast.LENGTH_LONG).show();
    }

    public void newFlight(View view) {
    }

    public void viewAirlinePretty(View view) {
    }

    public void searchFlightsBetweenTwoAirports(View view) {
    }

    public void saveAirlineToFile(View view) {
    }
}