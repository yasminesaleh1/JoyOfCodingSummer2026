package edu.pdx.cs.joy.ysaleh;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class newFlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_flight);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void backToMain(View view) {
        finish();
    }

    public void addNewFlight(View view) {
        // error for airline not existing
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This airline does not exist. Please enter an existing airline.").setTitle("Error: Airline doesn't exist")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); finish();
                    }
                });
        AlertDialog invalidAirlineDialog = builder.create();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Information is missing from at least one of the boxes. Please fill in every box for flight information.").setTitle("Error: Empty Fields")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog missingInformation = builder1.create();

        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage("Flight number must not contain anything other than numbers 0-9 and it cannot be negative. Please enter a positive flight number with no other characters.").setTitle("Error: Invalid Flight Number")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog invalidFlightNum = builder2.create();

        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
        builder3.setMessage("Source Airport code must only contain three characters and must not contain anything other than letters. Please enter a source airport code with only 3 letters.").setTitle("Error: Invalid Source Airport")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog invalidSrc = builder3.create();

        AlertDialog.Builder builder4 = new AlertDialog.Builder(this);
        builder4.setMessage("Destination Airport code must only contain three characters and must not contain anything other than letters. Please enter a destination airport code with only 3 letters.").setTitle("Error: Invalid Destination Airport")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog invalidDest = builder4.create();

        AlertDialog.Builder builder5 = new AlertDialog.Builder(this);
        builder5.setMessage("Departure date format is invalid. Please enter the departure date in the format MM/DD/YYYY HH:MM AM/PM").setTitle("Invalid Departure Date")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog invalidDepart = builder5.create();

        AlertDialog.Builder builder6 = new AlertDialog.Builder(this);
        builder6.setMessage("Arrival date format is invalid. Please enter the arrival date in the format MM/DD/YYYY HH:MM AM/PM").setTitle("Invalid Arrival Date")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog invalidArrive = builder6.create();

        EditText airlineNameWidget = findViewById(R.id.enter_flights_airline);
        String airlineName = airlineNameWidget.getText().toString();
        EditText flightNumWidget = findViewById(R.id.enter_flight_num);
        String flightNumString = flightNumWidget.getText().toString();
        EditText srcWidget = findViewById(R.id.enter_src);
        String src = srcWidget.getText().toString();
        EditText departWidget = findViewById(R.id.enter_depart);
        String depart = departWidget.getText().toString();
        EditText destWidget = findViewById(R.id.enter_dest);
        String dest = destWidget.getText().toString();
        EditText arriveWidget = findViewById(R.id.enter_arrive);
        String arrive = arriveWidget.getText().toString();

        if (airlineName.isEmpty() || flightNumString.isEmpty() || src.isEmpty()
                || depart.isEmpty() || dest.isEmpty() || arrive.isEmpty()) {
            missingInformation.show();
        }

        if (!flightNumString.matches("[0-9]+")) {
            invalidFlightNum.show();
        }

        if (!src.matches("[a-zA-Z]+") || !(src.matches(".{3}"))) {
            invalidSrc.show();
        }

        if (!dest.matches("[a-zA-Z]+") || !(dest.matches(".{3}"))) {
            invalidDest.show();
        }

        // invalid departure date/time
        if (!depart.matches("[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4} [0-9]{1,2}:[0-9]{2} (AM|PM)")) {
            invalidDepart.show();
        }

        if (!arrive.matches("[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4} [0-9]{1,2}:[0-9]{2} (AM|PM)")) {
            invalidArrive.show();
        }


        int flightNum = Integer.parseInt(flightNumString);
        String fileName = airlineName.toLowerCase() + ".txt";
        boolean airlineExists = false;

        String[] files = this.fileList();
        for (String f : files) {
            if (f.equals(fileName)) {
                airlineExists = true;

                // add new airline and store its contents into a file in internal storage
                Airline newAirline = new Airline(airlineName);
                Flight newFlight = new Flight(flightNum, src, depart, dest, arrive);
                newAirline.addFlight(newFlight);
                File airlineFile = new File(getApplicationContext().getFilesDir(), fileName);
                TextDumper td = new TextDumper(airlineFile);
                td.dump(newAirline);

                // inform the user the airline was successfully created
                Snackbar successSnackbar = Snackbar.make(view, "Flight Successfully Added!", 40);
                successSnackbar.show();
            }
        }
        if (!airlineExists) {
            invalidAirlineDialog.show(); // no airline matched
        }
    }
}