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

import java.io.File;

import edu.pdx.cs.joy.ParserException;

public class twoAirlinesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_two_airlines);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void backToMain(View view) {
        finish();
    }

    public void printFlights(View view) throws ParserException {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This airline does not exist. Please enter an existing airline.").setTitle("Error: Airline doesn't exist")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); finish();
                    }
                });
        AlertDialog invalidAirlineDialog = builder.create();

        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
        builder3.setMessage("Source Airport code must only contain three characters and must not contain anything other than letters. Please enter a source airport code with only 3 letters.").setTitle("Error: Invalid Source Airport")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog invalidSrc = builder3.create();

        AlertDialog.Builder builder4 = new AlertDialog.Builder(this);
        builder4.setMessage("Destination Airport code must only contain three characters and must not contain anything other than letters. Please enter a destination airport code with only 3 letters.").setTitle("Error: Invalid Destination Airport")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog invalidDest = builder4.create();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Information is missing from at least one of the boxes. Please fill in every box for flight information.").setTitle("Error: Empty Fields")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog missingInformation = builder1.create();
        

        EditText airlineNameWidget = findViewById(R.id.enter_airline_name);
        String airlineName = airlineNameWidget.getText().toString();
        EditText srcWidget = findViewById(R.id.enter_src);
        String src = srcWidget.getText().toString();
        EditText destWidget = findViewById(R.id.enter_dest);
        String dest = destWidget.getText().toString();

        if (airlineName.isEmpty() || src.isEmpty() || dest.isEmpty()) {
            missingInformation.show();
            return;
        }
        if (!src.matches("[a-zA-Z]+") || !(src.matches(".{3}"))) {
            invalidSrc.show();
            return;
        }

        if (!dest.matches("[a-zA-Z]+") || !(dest.matches(".{3}"))) {
            invalidDest.show();
            return;
        }

        String fileName = airlineName.toLowerCase() + ".txt";
        boolean airlineExists = false;
        String prettyPrintedFlights = null;

        String[] files = this.fileList();
        for (String f : files) {
            if (f.equals(fileName)) {
                airlineExists = true;
                File file = new File(this.getFilesDir(), fileName);

                TextParser tp = new TextParser(file);
                Airline retrievedAirline = tp.parse();
                PrettyPrinter p = new PrettyPrinter();
                prettyPrintedFlights = p.dump(retrievedAirline, src, dest);

                EditText printingBox = findViewById(R.id.displayedAirline);
                printingBox.setText(prettyPrintedFlights);
            }
        }
        if (!airlineExists) {
            invalidAirlineDialog.show(); // no airline matched
        }

    }
}